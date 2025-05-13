package com.example.api_automation.utils;

import org.springframework.beans.factory.annotation.Value;

public class APIUrlBuilder {

    @Value("${default.extension}")
    private String apiExtension;

    public String getUrl(String... endpointExtensions){
        StringBuilder url = new StringBuilder();
        for (String endpointExtension: endpointExtensions){
            url.append(endpointExtension);
        }
        return url.toString();
    }
}
