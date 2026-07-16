package com.nd.urlshortener.Service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nd.urlshortener.Entity.UrlMapping;
import com.nd.urlshortener.Repository.UrlRepository;
import com.nd.urlshortener.dto.UrlRequest;
import com.nd.urlshortener.dto.UrlStatsResponse;
import com.nd.urlshortener.exception.AliasAlreadyExistsException;
import com.nd.urlshortener.exception.UrlNotFoundException;

@Service
public class UrlService {
    private final UrlRepository urlRepo;
    @Value("${app.base-url}")
    private String baseUrl;
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
        return baseUrl+existingUrl.get().getShortCode();
    }else if(customAlias==null || customAlias.isBlank()) {
        shortCode=generateShortCode();
        while(urlRepo.existsByShortCode(shortCode)){
            shortCode=generateShortCode();
        }
        
        }
        else{
           shortCode=request.getCustomAlias();
           if(urlRepo.existsByShortCode(shortCode)){
            throw new AliasAlreadyExistsException("Custom alias already exists");
           }
        }
         UrlMapping mapping=new UrlMapping();
        mapping.setOrriginalUrl(request.getUrl());
        mapping.setShortCode(shortCode);
        mapping.setClickCount(0L);
        urlRepo.save(mapping);
        return baseUrl+shortCode;
       
    }
    //redirecting to original Url
    public String getOriginalUrl(String shortCode){
         UrlMapping mapping = urlRepo.findByShortCode(shortCode)
            .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));

    mapping.setClickCount(mapping.getClickCount() + 1);

    urlRepo.save(mapping);

    return mapping.getOrriginalUrl();
    }
    //Stats 
    public UrlStatsResponse getStats(String shortCode) {

    UrlMapping mapping = urlRepo.findByShortCode(shortCode)
            .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));

    return new UrlStatsResponse(
            mapping.getOrriginalUrl(),
            mapping.getShortCode(),
            mapping.getClickCount()
    );
}
}
