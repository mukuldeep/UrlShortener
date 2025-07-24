package com.offlinew.urlshortener.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlShortenerService {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final ConcurrentHashMap<String, String> urlToCode = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> codeToUrl = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(100_000);

    public String shortenUrl(String originalUrl) {
        if (urlToCode.containsKey(originalUrl)) {
            return urlToCode.get(originalUrl);
        }

        String code = encodeBase62(idCounter.getAndIncrement());
        urlToCode.put(originalUrl, code);
        codeToUrl.put(code, originalUrl);
        return code;
    }

    public String getOriginalUrl(String shortCode) {
        return codeToUrl.get(shortCode);
    }

    private String encodeBase62(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(BASE62.charAt((int)(value % 62)));
            value /= 62;
        }
        return sb.reverse().toString();
    }
}
