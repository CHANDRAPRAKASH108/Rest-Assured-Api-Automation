package com.example.api_automation.tests;

import com.example.api_automation.pojo.AddPostPojo;
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
@Feature("Add Post Test")
@Listeners({AllureTestNg.class})
public class AddPostTest extends PageObjects {

    private AddPostPojo addPostResponseObj;

    @Test(priority = 0)
    @Description("Add post")
    @Severity(SeverityLevel.BLOCKER)
    public void addPost(){
        RegisterUserPojo registerUserPojo = (RegisterUserPojo) sharedContext.getAttribute("postRegisterResponse");
        AddPostPojo addPostPojoReq = prepareRequestBody("addPostPayload.json", AddPostPojo.class, addPostPojo1 -> {
            addPostPojo1.setUsername(registerUserPojo.getUsername());
        });
        Response response = addPostForUser(addPostPojoReq);
        responseCode201(response);
        addPostResponseObj = extractObjectFromResponse(response, AddPostPojo.class);
        responseSchemaValidation(response, "addPostJsonSchema.json");
    }

    @Test(priority = 1)
    @Description("Fetch post")
    @Severity(SeverityLevel.BLOCKER)
    public void fetchPosts(){
        Response response = fetchPostForUser();
        responseCodeOK(response);
        List<AddPostPojo> addGetResponseObj = extractListOfObjectFromResponse(response, AddPostPojo.class);
        boolean postExists = addGetResponseObj.stream()
                .anyMatch(addPostPojo -> addPostPojo.getUsername().equals(addPostResponseObj.getUsername()));
        Assert.assertTrue(postExists);
    }

    @Test(priority = 2)
    @Description("Fetch post by id")
    @Severity(SeverityLevel.BLOCKER)
    public void fetchPostById(){
        Response response = fetchPostForUserById(addPostResponseObj);
        responseCodeOK(response);
        AddPostPojo addGetResponseObj = extractObjectFromResponse(response, AddPostPojo.class);
        Assert.assertTrue(addGetResponseObj.getUsername().equals(addPostResponseObj.getUsername()));
        responseSchemaValidation(response, "addPostJsonSchema.json");
    }

    @Test(priority = 3)
    @Description("Fetch post by id")
    @Severity(SeverityLevel.BLOCKER)
    public void updatePostById(){
        addPostResponseObj.setUsername(String.valueOf(Math.random()));
        Response response = updatePostForUserById(addPostResponseObj);
        responseCodeOK(response);
        AddPostPojo addGetResponseObj = extractObjectFromResponse(response, AddPostPojo.class);
        Assert.assertTrue(addGetResponseObj.getUsername().equals(addPostResponseObj.getUsername()));
        responseSchemaValidation(response, "addPostJsonSchema.json");
    }

    @Test(priority = 4)
    @Description("Fetch post by id")
    @Severity(SeverityLevel.BLOCKER)
    public void deletePostById(){
        Response response = deletePostForUserById(addPostResponseObj);
        responseCodeOK(response);
    }
}
