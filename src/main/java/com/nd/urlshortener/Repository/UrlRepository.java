package com.nd.urlshortener.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nd.urlshortener.Entity.UrlMapping;

@Repository
public interface  UrlRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping>findByShortCode(String shortCode);
    Optional<UrlMapping>findByOriginalUrl(String originalUrl);
    boolean existsByShortCode(String shortCode);
    
}
