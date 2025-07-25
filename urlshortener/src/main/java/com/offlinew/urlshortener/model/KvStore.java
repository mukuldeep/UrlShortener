package com.offlinew.urlshortener.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "kvStore")
@Data
@AllArgsConstructor
public class KvStore {
    @Indexed(unique = true)
    @Id
    private String key;
    private String value;

}



