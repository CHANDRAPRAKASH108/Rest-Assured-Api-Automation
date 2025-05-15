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
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@SpringBootTest
@Feature("Register User Test")
@Listeners({AllureTestNg.class})
public class RegisterUserTest extends PageObjects {

    private RegisterUser postRegisterUser;

    @Test
    @Description("Register user with valid details")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithValidDetails(){
        RegisterUser registerUserObject = prepareRequestBody("registerUserPayload.json", RegisterUser.class, registerUser -> {
            registerUser.setName("Name"+ Math.random());
        });
        Response response = registerUser(registerUserObject);
        responseCode201(response);
        postRegisterUser = extractObjectFromResponse(response, RegisterUser.class);
        System.out.println("AAAAA"+ postRegisterUser.getName());
        sharedContext.setAttribute("postRegisterResponse", postRegisterUser);
        responseSchemaValidation(response, "registerUserSchema.json");
    }

    @Test(dependsOnMethods = "registerUserWithValidDetails")
    @Description("Register user without required param")
    @Severity(SeverityLevel.BLOCKER)
    public void fetchAndValidateRegisteredUser(){
        Response response = fetchRegisteredUser();
        responseCodeOK(response);
        List<RegisterUser> registerUser = extractListOfObjectFromResponse(response, RegisterUser.class);
        responseSchemaValidation(response, "gerRegisterUserListSchema.json");
        boolean userExists = registerUser.stream()
                .anyMatch(user -> user.getId().equals(postRegisterUser.getId()));
        Assert.assertTrue(userExists);
    }
}
