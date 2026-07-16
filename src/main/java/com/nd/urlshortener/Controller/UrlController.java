package com.nd.urlshortener.Controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.nd.urlshortener.Service.UrlService;
import com.nd.urlshortener.dto.UrlRequest;
import com.nd.urlshortener.dto.UrlResponse;
import com.nd.urlshortener.dto.UrlStatsResponse;

import jakarta.validation.Valid;

@RestController

public class UrlController {
    private final UrlService urlService;
    public UrlController(UrlService urlService){
        this.urlService=urlService;
    }
    @PostMapping("/api/shorten")
    public UrlResponse shorten(@Valid @RequestBody UrlRequest request){
        String shortUrl=urlService.createShortUrl(request);
        return new UrlResponse(shortUrl);
        
    }

    //redirecting
    @GetMapping("/{shortCode}")
public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

    String originalUrl = urlService.getOriginalUrl(shortCode);

    return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI.create(originalUrl))
            .build();
}
    @GetMapping("/api/stats/{shortCode}")
    public ResponseEntity<UrlStatsResponse>getStats(@PathVariable String shortCode ){
        UrlStatsResponse stats=urlService.getStats(shortCode);
        return ResponseEntity.ok(stats);
    }
}
