package com.example.api_automation.service;

import com.example.api_automation.ApiAutomationApplication;
import com.example.api_automation.utils.SpecBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.TestException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;

import static com.example.api_automation.utils.Constants.JSON_DIR;
import static com.example.api_automation.utils.Constants.JSON_SCHEMA_PATH;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@SpringBootTest(classes = ApiAutomationApplication.class)
@Listeners({AllureTestNg.class})
public class BaseTest extends AbstractTestNGSpringContextTests {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    protected static ITestContext sharedContext;

    @Autowired
    SpecBuilder specBuilder;

    @BeforeTest
    public void setSharedContext(ITestContext context){
        sharedContext = context;
    }

    protected <T> List<T> readJsonFileAsJavaObjectList(String filePath, Class<T> clazz) throws IOException {
        CollectionType collectionType = mapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, clazz);
        return mapper.readValue(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filePath)), collectionType);
    }

    protected <T> T readJsonFileAsJavaObject(String filePath, Class<T> clazz) throws IOException {
        return mapper.readValue(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filePath)), clazz);
    }



    @Step
    public void responseCodeOK(Response response) {
        int actual = response.then().extract().statusCode();
        Assert.assertEquals(actual, 200);
    }

    @Step
    public void responseCode201(Response response) {
        int actual = response.then().extract().statusCode();
        Assert.assertEquals(actual, 201);
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
    public Response postRequest(Object payload, String endpoint, Map<String, String> queryParam, Map<String, String> headers) {
        return given(specBuilder.requestSpecBuilder(queryParam, headers))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .filter(new AllureRestAssured())
                .when()
                .body(payload)
                .post(endpoint);
    }

    @Step
    public Response getRequest(String endpoint, Map<String, String> queryParam, Map<String, String> headers) {
        return given(specBuilder.requestSpecBuilder(queryParam, headers))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .filter(new AllureRestAssured())
                .when()
                .get(endpoint);
    }

    @Step
    public Response putRequest(Object payload, String endpoint, Map<String, String> queryParam, Map<String, String> headers) {
        return given(specBuilder.requestSpecBuilder(queryParam, headers))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .filter(new AllureRestAssured())
                .when()
                .body(payload)
                .put(endpoint);
    }

    @Step
    public Response deleteRequest(String endpoint, Map<String, String> queryParam, Map<String, String> headers) {
        return given(specBuilder.requestSpecBuilder(queryParam, headers))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .filter(new AllureRestAssured())
                .when()
                .delete(endpoint);
    }

    @Step
    public <T> T prepareRequestBody(String jsonFileName, Class<T> clazz) {
        String jsonPath = Paths.get("json", JSON_DIR).resolve(Paths.get(jsonFileName)).toString();
        try {
            return readJsonFileAsJavaObject(jsonPath, clazz);
        } catch (IOException e) {
            throw new TestException(e.getMessage());
        }
    }

    @Step
    public <T> T prepareRequestBody(String jsonFileName, Class<T> clazz, Consumer<T> consumer) {
        String jsonPath = Paths.get("json", JSON_DIR).resolve(Paths.get(jsonFileName)).toString();
        try {
            T parsedObject = readJsonFileAsJavaObject(jsonPath, clazz);
            if (Objects.nonNull(consumer)) {
                consumer.accept(parsedObject);
            }
            return parsedObject;
        } catch (IOException e) {
            throw new TestException(e.getMessage());
        }
    }

    @Step
    public <T> List<T> prepareListRequestBody(String jsonFileName, Class<T> clazz) {
        String jsonPath = Paths.get("json", JSON_DIR).resolve(Paths.get(jsonFileName)).toString();
        try {
            return readJsonFileAsJavaObjectList(jsonPath, clazz);
        } catch (IOException e) {
            throw new TestException(e.getMessage());
        }
    }

    @Step
    public <T> List<T> prepareListRequestBody(String jsonFileName, Class<T> clazz, Consumer<T> consumer) {
        String jsonPath = Paths.get("json", JSON_DIR).resolve(Paths.get(jsonFileName)).toString();
        try {
            List<T> parsedObject = readJsonFileAsJavaObjectList(jsonPath, clazz);
            if (Objects.nonNull(consumer)) {
                parsedObject.forEach(consumer::accept);
            }
            return parsedObject;
        } catch (IOException e) {
            throw new TestException(e.getMessage());
        }
    }

    @Step
    public <T> T extractObjectFromResponse(Response response, Class<T> clazz) {
        String responseBody = response.getBody().toString();
        if (responseBody == null) {
            return null;
        }
        return response.as(clazz);
    }

    @Step
    public <T> List<T> extractListOfObjectFromResponse(Response response, Class<T> clazz) {
        try {
            return mapper.readValue(response.getBody().asString(),
                    mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Step
    public void responseSchemaValidation(Response response, String fileName){
        response.then().assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA_PATH + fileName));
    }
}
