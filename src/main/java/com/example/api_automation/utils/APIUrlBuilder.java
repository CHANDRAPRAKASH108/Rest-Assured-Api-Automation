package com.example.api_automation.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APIUrlBuilder {

    @Value("${default.extension}")
    private String apiExtension;

    public String getUrl(String... endpointExtensions){
        StringBuilder url = new StringBuilder(apiExtension);
        for (String endpointExtension: endpointExtensions){
            url.append(endpointExtension);
        }
        return url.toString();
    }
}
