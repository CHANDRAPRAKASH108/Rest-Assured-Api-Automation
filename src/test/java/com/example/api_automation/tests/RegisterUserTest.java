package com.example.api_automation.tests;

import com.example.api_automation.pojo.RegisterUser;
import com.example.api_automation.service.PageObjects;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@SpringBootTest
@Feature("Register User Test")
@Listeners({AllureTestNg.class})
public class RegisterUserTest extends PageObjects {

    @Test
    @Description("Register user with valid details")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithValidDetails(){
        RegisterUser registerUserObject = prepareRequestBody("registerUserPayload.json", RegisterUser.class);
        Response response = registerUser(registerUserObject);
        responseCode201(response);
        RegisterUser registerUser = extractObjectFromResponse(response, RegisterUser.class);
        sharedContext.setAttribute("postRegisterResponse", registerUser);
        responseSchemaValidation(response, "registerUserSchema.json");
    }
}
