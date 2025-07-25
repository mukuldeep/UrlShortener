package com.offlinew.urlshortener.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "url_mappings")
@Data
public class UrlMapping {

    @Id
    private String id;

    @Indexed(unique = true)
    private String originalUrl;

    @Indexed(unique = true)
    private String shortCode;

    public UrlMapping(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
    }

}



