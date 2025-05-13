package com.example.api_automation.service;

import com.example.api_automation.utils.SpecBuilder;
import io.qameta.allure.Step;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {

    @Autowired
    SpecBuilder specBuilder;

    @Step
    public void responseCodeOK(Response response) {
        int actual = response.then().extract().statusCode();
        Assert.assertEquals(actual, 200);
    }

    @Step
    public void responseCode4XX(Response response) {
        int actual = response.then().extract().statusCode();
        Assert.assertEquals(actual, 400);
    }

    @Step
    public void responseCode204(Response response) {
        int actual = response.then().extract().statusCode();
        Assert.assertEquals(actual, 204);
    }

    @Step
    public void responseCode404(Response response) {
        int actual = response.then().extract().statusCode();
        Assert.assertEquals(actual, 404);
    }


    @Step
    public Response postRequest(Object payload, String endpoint){
        return given(specBuilder.requestSpecBuilder(null, null))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when()
                .body(payload)
                .post(endpoint);
    }

    @Step
    public Response postRequestWithQueryParam(Object payload, String endpoint, Map<String, String> queryParam){
        return given(specBuilder.requestSpecBuilder(queryParam, null))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when()
                .body(payload)
                .post(endpoint);
    }

    @Step
    public Response postRequestWithAdditionalHeader(Object payload, String endpoint, Map<String, String> headers){
        return given(specBuilder.requestSpecBuilder(null, headers))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when()
                .body(payload)
                .post(endpoint);
    }
}
