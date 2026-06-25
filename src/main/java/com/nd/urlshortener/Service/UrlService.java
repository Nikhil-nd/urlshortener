package com.nd.urlshortener.Service;

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
    public String createShortUrl(String originalUrl){
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
