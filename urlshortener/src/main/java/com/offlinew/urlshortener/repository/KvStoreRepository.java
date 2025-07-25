package com.offlinew.urlshortener.repository;

import com.offlinew.urlshortener.model.KvStore;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KvStoreRepository extends MongoRepository<KvStore, String> {
    KvStore findByKey(String key);
    void deleteByKey(String key);

    KvStore findByValue(String value);
    void deleteByValue(String value);

}
