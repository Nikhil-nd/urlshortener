package com.nd.urlshortener.Service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nd.urlshortener.Entity.UrlMapping;
import com.nd.urlshortener.Repository.UrlRepository;

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
public String createShortUrl(String originalUrl){
    Optional<UrlMapping> existingUrl=urlRepo.findByOriginalUrl(originalUrl);
    if(existingUrl.isPresent()){  
        return "http://localhost:8080/"+existingUrl.get().getShortCode();
    }else{
        String shortCode=generateShortCode();
        while(urlRepo.existsByShortCode(shortCode)){
            shortCode=generateShortCode();
        }
        UrlMapping mapping=new UrlMapping();
        mapping.setOrriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        urlRepo.save(mapping);
        return "http://localhost:8080/"+shortCode;
        }
    }
    //redirecting to original Url
    public String getOriginalUrl(String shortCode){
        Optional<UrlMapping> mapping=urlRepo.findByShortCode(shortCode);
        if(!mapping.isPresent()){return null;}
        return mapping.get().getOrriginalUrl();
    }
}
