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

    public RequestSpecification requestSpecBuilder(Map<String, String> queryParams, Map<String, String> headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON);

        if (headers != null && !headers.isEmpty()) {
            builder.addHeaders(headers);
        }
        if (queryParams != null && !queryParams.isEmpty()) {
            builder.addQueryParams(queryParams);
        }
        if (queryParams != null && !queryParams.isEmpty() && headers != null && !headers.isEmpty()) {
            builder.addQueryParams(queryParams);
            builder.addHeaders(headers);
        }
        return builder.build();
    }
}
