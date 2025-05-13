package com.example.api_automation.utils;

import org.springframework.beans.factory.annotation.Value;
public class APIUrlBuilder {

    @Value("${default.baseUrl}")
    private String baseUrl;

    public String getUrl(String... endpointExtensions){
        StringBuilder url = new StringBuilder(baseUrl);
        for (String endpointExtension: endpointExtensions){
            url.append(endpointExtension);
        }
        return url.toString();
    }
}
