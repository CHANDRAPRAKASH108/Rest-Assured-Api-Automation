package com.example.api_automation.tests;

import com.example.api_automation.pojo.RegisterUserPojo;
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
public class RegisterUserPojoTest extends PageObjects {

    private RegisterUserPojo postRegisterUserPojo;

    @Test(priority = 1)
    @Description("Register user with valid details")
    @Severity(SeverityLevel.BLOCKER)
    public void registerUserWithValidDetails(){
        RegisterUserPojo registerUserPojoObject = prepareRequestBody("registerUserPayload.json", RegisterUserPojo.class, registerUserPojo -> {
            registerUserPojo.setName("Name"+ Math.random());
        });
        Response response = registerUser(registerUserPojoObject);
        responseCode201(response);
        postRegisterUserPojo = extractObjectFromResponse(response, RegisterUserPojo.class);
        System.out.println("AAAAA"+ postRegisterUserPojo.getName());
        sharedContext.setAttribute("postRegisterResponse", postRegisterUserPojo);
        responseSchemaValidation(response, "registerUserSchema.json");
    }

    @Test(dependsOnMethods = "registerUserWithValidDetails", priority = 2)
    @Description("Fetch and validate registered user")
    @Severity(SeverityLevel.BLOCKER)
    public void fetchAndValidateRegisteredUser(){
        Response response = fetchRegisteredUser();
        responseCodeOK(response);
        List<RegisterUserPojo> registerUserPojo = extractListOfObjectFromResponse(response, RegisterUserPojo.class);
        responseSchemaValidation(response, "gerRegisterUserListSchema.json");
        boolean userExists = registerUserPojo.stream()
                .anyMatch(user -> user.getId().equals(postRegisterUserPojo.getId()));
        Assert.assertTrue(userExists);
    }

    @Test(dependsOnMethods = "registerUserWithValidDetails", priority = 3)
    @Description("Fetch and validate registered user by userId")
    @Severity(SeverityLevel.BLOCKER)
    public void fetchRegisterUserById(){
        Response response = fetchRegisteredUserById(postRegisterUserPojo.getId().toString());
        responseCodeOK(response);
        RegisterUserPojo registerUserPojo = extractObjectFromResponse(response, RegisterUserPojo.class);
        responseSchemaValidation(response, "registerUserSchema.json");
        Assert.assertTrue(registerUserPojo.getEmail().equals(postRegisterUserPojo.getEmail()));
    }

    @Test(dependsOnMethods = "registerUserWithValidDetails", priority = 4)
    @Description("Update registered user detail")
    @Severity(SeverityLevel.BLOCKER)
    public void updateRegisteredUser(){
        postRegisterUserPojo.setName("Name"+Math.random());
        postRegisterUserPojo.setUsername(String.valueOf(Math.random()));
        Response response = updateRegisteredUserById(postRegisterUserPojo);
        responseCodeOK(response);
        RegisterUserPojo registerUserPojo = extractObjectFromResponse(response, RegisterUserPojo.class);
        responseSchemaValidation(response, "registerUserSchema.json");
        Assert.assertTrue(registerUserPojo.getName().equals(postRegisterUserPojo.getName()) && registerUserPojo.getEmail().equals(postRegisterUserPojo.getEmail()));
    }

    @Test(dependsOnMethods = "registerUserWithValidDetails", priority = 5)
    @Description("delete registered user detail")
    @Severity(SeverityLevel.BLOCKER)
    public void deleteRegisteredUser(){
        Response response = deleteRegisteredUserById(postRegisterUserPojo.getId().toString());
        responseCodeOK(response);
    }
}
