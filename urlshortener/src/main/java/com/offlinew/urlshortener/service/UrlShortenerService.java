package com.offlinew.urlshortener.service;

import com.offlinew.urlshortener.model.KvStore;
import com.offlinew.urlshortener.model.UrlMapping;
import com.offlinew.urlshortener.repository.KvStoreRepository;
import com.offlinew.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlShortenerService {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private KvStoreRepository kvStoreRepository;

    private AtomicLong idCounter = new AtomicLong(100_000);

    public String shortenUrl(String originalUrl) {
        return urlMappingRepository.findByOriginalUrl(originalUrl)
                .map(UrlMapping::getShortCode)
                .orElseGet(() -> {
                    KvStore kvStore = kvStoreRepository.findByKey("seq");
                    if(kvStore == null){
                        kvStore = new KvStore("seq","10000000");
                        kvStoreRepository.save(kvStore);
                    }
                    String latestUsed = kvStore.getValue();
                    long idCounterDb = Long.parseLong(latestUsed);
                    idCounter.set(Math.max(idCounter.get(),idCounterDb));

                    String code = encodeBase62(idCounter.incrementAndGet());
                    UrlMapping urlMapping = new UrlMapping(originalUrl, code);

                    //expiry timestamp
                    long currentTimestamp = System.currentTimeMillis() / 1000;
                    long expiryTimestamp = currentTimestamp + 2 * 60; //default expiry = 10 minutes
                    urlMapping.setExpiaryTs(expiryTimestamp);

                    urlMappingRepository.save(urlMapping);

                    kvStore.setValue(String.valueOf(idCounter.get()));
                    kvStoreRepository.save(kvStore);

                    return code;
                });
    }

    public String getOriginalUrl(String shortCode) {

        //increase visit count
        Optional<UrlMapping> urlMappingOp = urlMappingRepository.findByShortCode(shortCode);
        UrlMapping urlMapping = null;
        if(urlMappingOp.isPresent()){
            urlMapping = urlMappingOp.get();
            Long count = urlMapping.getVisitCount();
            count = (count == null)?1L:count+1;
            urlMapping.setVisitCount(count);

            //block if expired
            long currentTimestamp = System.currentTimeMillis() / 1000;
            if(urlMapping.getExpiaryTs() <= currentTimestamp){
                return null;
            }

            urlMappingRepository.save(urlMapping);
        }

        return urlMappingOp
                .map(UrlMapping::getOriginalUrl)
                .orElse(null);
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
