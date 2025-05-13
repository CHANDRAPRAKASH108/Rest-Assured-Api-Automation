package com.example.api_automation.tests;

import com.example.api_automation.pojo.RegisterUser;
import com.example.api_automation.service.BaseTest;
import com.example.api_automation.service.PageObjects;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class RegisterUserTest extends PageObjects {

    @Test
    public void registerUserWithValidDetails(){
        RegisterUser registerUserObject = prepareRequestBody("registerUserPayload.json", RegisterUser.class);
        Response response = registerUser(registerUserObject);
        responseCodeOK(response);
    }
}
