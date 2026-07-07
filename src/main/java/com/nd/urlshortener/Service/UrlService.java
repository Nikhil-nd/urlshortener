package com.nd.urlshortener.Service;

import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nd.urlshortener.Entity.UrlMapping;
import com.nd.urlshortener.Repository.UrlRepository;
import com.nd.urlshortener.dto.UrlRequest;
import com.nd.urlshortener.dto.UrlStatsResponse;

@Service
public class UrlService {
    private final UrlRepository urlRepo;
    public UrlService(UrlRepository urlRepo){
        this.urlRepo=urlRepo;
    }

    //generating the shortCode
    private String generateShortCode(){
        String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb=new StringBuilder();
        Random random=new Random();
        for(int i=0;i<6;i++){
            sb.append(
                chars.charAt(
                    random.nextInt(chars.length())));
        }
    return sb.toString();}
    //creating the Shortcode
public String createShortUrl(UrlRequest request){
    String customAlias=request.getCustomAlias();
    String shortCode;
    Optional<UrlMapping> existingUrl=urlRepo.findByOriginalUrl(request.getUrl());
    if(existingUrl.isPresent()){  
        return "http://localhost:8080/"+existingUrl.get().getShortCode();
    }else if(customAlias==null || customAlias.isBlank()) {
        shortCode=generateShortCode();
        while(urlRepo.existsByShortCode(shortCode)){
            shortCode=generateShortCode();
        }
        
        }
        else{
           shortCode=request.getCustomAlias();
           if(urlRepo.existsByShortCode(shortCode)){
            throw new RuntimeException("Alias already exists");
           }
        }
         UrlMapping mapping=new UrlMapping();
        mapping.setOrriginalUrl(request.getUrl());
        mapping.setShortCode(shortCode);
        mapping.setClickCount(0L);
        urlRepo.save(mapping);
        return "http://localhost:8080/"+shortCode;
       
    }
    //redirecting to original Url
    public String getOriginalUrl(String shortCode){
        Optional<UrlMapping> mapping=urlRepo.findByShortCode(shortCode);
        if(!mapping.isPresent()){return null;}
        mapping.get().setClickCount(mapping.get().getClickCount()==null ?1L :mapping.get().getClickCount()+1);
        urlRepo.save(mapping.get());
        // System.out.println(mapping.get().getClickCount());
        return mapping.get().getOrriginalUrl();
    }
    //Stats 
    public UrlStatsResponse getStats(String shortCode) {
        Optional<UrlMapping>mapping=urlRepo.findByShortCode(shortCode);
        if(mapping.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No Url found with this short Code");
        }
      return new UrlStatsResponse(mapping.get().getOrriginalUrl(),
                                    mapping.get().getShortCode(),
                                    mapping.get().getClickCount());
    }
}
