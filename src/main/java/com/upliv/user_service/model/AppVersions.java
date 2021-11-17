package com.upliv.user_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;

@Document
public class AppVersions {
    @Id
    private String id;
    private LinkedHashMap<String,Boolean> androidVersions;
    private LinkedHashMap<String,Boolean> iosVersions;

    public AppVersions() {
        this.id = "Versions";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedHashMap<String, Boolean> getAndroidVersions() {
        return androidVersions;
    }

    public void setAndroidVersions(LinkedHashMap<String, Boolean> androidVersions) {
        this.androidVersions = androidVersions;
    }

    public LinkedHashMap<String, Boolean> getIosVersions() {
        return iosVersions;
    }

    public void setIosVersions(LinkedHashMap<String, Boolean> iosVersions) {
        this.iosVersions = iosVersions;
    }
}
