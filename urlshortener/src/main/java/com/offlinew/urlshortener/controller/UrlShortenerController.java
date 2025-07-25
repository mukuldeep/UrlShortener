package com.offlinew.urlshortener.controller;

import com.offlinew.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;
    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody String originalUrl) {
        String code = urlShortenerService.shortenUrl(originalUrl);
        return ResponseEntity.ok("http://localhost:8080/" + code);
    }

    @GetMapping("/{code}")
    public RedirectView redirect(@PathVariable String code) {
        String originalUrl = urlShortenerService.getOriginalUrl(code);
        if (originalUrl == null) {
            return new RedirectView("/error");
        }
        return new RedirectView(originalUrl);
    }
}
