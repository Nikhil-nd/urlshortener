package com.nd.urlshortener.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.nd.urlshortener.Service.UrlService;
import com.nd.urlshortener.dto.UrlRequest;
import com.nd.urlshortener.dto.UrlResponse;

@RestController
@RequestMapping("/api")
public class UrlController {
    private UrlService urlService;
    public UrlController(UrlService urlService){
        this.urlService=urlService;
    }
    @PostMapping("/response")
    public UrlResponse shorten(@RequestBody UrlRequest request){
        String shortUrl=urlService.createShortUrl(request.getUrl());
        return new UrlResponse(shortUrl);
    }
}
