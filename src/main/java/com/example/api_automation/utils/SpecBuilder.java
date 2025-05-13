package com.example.api_automation.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpecBuilder {

    @Value("${default.baseUrl}")
    private String baseUrl;

    public RequestSpecification requestSpecBuilder(){
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Content-Type", "application/json")
                .setContentType(ContentType.JSON)
                .build();
    }

    public RequestSpecification requestSpecBuilderWithQueryParam(Map<String, String> queryMap){
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Content-Type", "application/json")
                .addQueryParams(queryMap)
                .setContentType(ContentType.JSON)
                .build();
    }

    public RequestSpecification requestSpecBuilderWithAdditionalHeader(Map<String, String> headerList){
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Content-Type", "application/json")
                .addHeaders(headerList)
                .setContentType(ContentType.JSON)
                .build();
    }
}
