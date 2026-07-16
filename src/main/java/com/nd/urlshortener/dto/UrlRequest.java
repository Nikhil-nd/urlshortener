package com.nd.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;

public class UrlRequest {
    @NotBlank(message="Url cannot be blank")
private String url;

private String customAlias;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

}
