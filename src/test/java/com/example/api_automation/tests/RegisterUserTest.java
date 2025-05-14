package com.example.api_automation.tests;

import com.example.api_automation.pojo.RegisterUser;
import com.example.api_automation.service.PageObjects;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

@SpringBootTest
public class RegisterUserTest extends PageObjects {

    @Test
    public void registerUserWithValidDetails(){
        RegisterUser registerUserObject = prepareRequestBody("registerUserPayload.json", RegisterUser.class);
        Response response = registerUser(registerUserObject);
        responseCode201(response);
    }
}
