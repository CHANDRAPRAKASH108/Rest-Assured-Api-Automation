package com.example.api_automation.service;

import com.example.api_automation.pojo.RegisterUser;
import com.example.api_automation.utils.APIUrlBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.api_automation.endpoint.EndpointConstants.REGISTER_USER_ENDPOINT;


@Component
public class PageObjects extends BaseTest{

    @Autowired
    protected APIUrlBuilder apiUrlBuilder;

    @Step("Register User")
    public Response registerUser(RegisterUser registerUser){
        return postRequest(registerUser, apiUrlBuilder.getUrl(REGISTER_USER_ENDPOINT));
    }
}
