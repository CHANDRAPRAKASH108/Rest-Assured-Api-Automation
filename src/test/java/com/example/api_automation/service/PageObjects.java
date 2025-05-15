package com.example.api_automation.service;

import com.example.api_automation.pojo.AddPostPojo;
import com.example.api_automation.pojo.RegisterUserPojo;
import com.example.api_automation.utils.APIUrlBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.api_automation.endpoint.EndpointConstants.ADD_POST_ENDPOINT;
import static com.example.api_automation.endpoint.EndpointConstants.REGISTER_USER_ENDPOINT;


@Component
public class PageObjects extends BaseTest {

    @Autowired
    protected APIUrlBuilder apiUrlBuilder;

    @Step("Register User")
    public Response registerUser(RegisterUserPojo registerUserPojo) {
        return postRequest(registerUserPojo, apiUrlBuilder.getUrl(REGISTER_USER_ENDPOINT), null, null);
    }

    @Step("Fetch Register User")
    public Response fetchRegisteredUser() {
        return getRequest(apiUrlBuilder.getUrl(REGISTER_USER_ENDPOINT), null, null);
    }

    @Step("Fetch Register User By Id")
    public Response fetchRegisteredUserById(String Id) {
        return getRequest(apiUrlBuilder.getUrl(REGISTER_USER_ENDPOINT) + "/" + Id, null, null);
    }

    @Step("Fetch Register User By Id")
    public Response updateRegisteredUserById(RegisterUserPojo registerUserPojo) {
        return putRequest(registerUserPojo,apiUrlBuilder.getUrl(REGISTER_USER_ENDPOINT) + "/" + registerUserPojo.getId(), null, null);
    }

    @Step("Delete Register User By Id")
    public Response deleteRegisteredUserById(String Id) {
        return deleteRequest(apiUrlBuilder.getUrl(REGISTER_USER_ENDPOINT) + "/" + Id, null, null);
    }

    @Step("Add Post")
    public Response addPostForUser(AddPostPojo addPostPojo) {
        return postRequest(addPostPojo, apiUrlBuilder.getUrl(ADD_POST_ENDPOINT), null, null);
    }

    @Step("Fetch Post")
    public Response fetchPostForUser() {
        return getRequest(apiUrlBuilder.getUrl(ADD_POST_ENDPOINT), null, null);
    }

    @Step("Fetch Post By ID")
    public Response fetchPostForUserById(AddPostPojo addPostPojo) {
        return getRequest(apiUrlBuilder.getUrl(ADD_POST_ENDPOINT)+"/"+addPostPojo.getId(), null, null);
    }

    @Step("Fetch Post By ID")
    public Response updatePostForUserById(AddPostPojo addPostPojo) {
        return putRequest(addPostPojo, apiUrlBuilder.getUrl(ADD_POST_ENDPOINT)+"/"+addPostPojo.getId(), null, null);
    }

    @Step("Delete Post By ID")
    public Response deletePostForUserById(AddPostPojo addPostPojo) {
        return deleteRequest( apiUrlBuilder.getUrl(ADD_POST_ENDPOINT)+"/"+addPostPojo.getId(), null, null);
    }
}
