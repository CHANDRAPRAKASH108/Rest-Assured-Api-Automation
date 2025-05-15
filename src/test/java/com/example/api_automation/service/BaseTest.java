package com.example.api_automation.service;

import com.example.api_automation.ApiAutomationApplication;
import com.example.api_automation.utils.SpecBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static com.example.api_automation.utils.Constants.JSON_DIR;
import static io.restassured.RestAssured.given;

@SpringBootTest(classes = ApiAutomationApplication.class)
@Listeners({AllureTestNg.class})
public class BaseTest extends AbstractTestNGSpringContextTests {

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    SpecBuilder specBuilder;

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
    public Response postRequest(Object payload, String endpoint) {
        return given(specBuilder.requestSpecBuilder(null, null))
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when()
                .body(payload)
                .post(endpoint);
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
    public <T> List<T> prepareListRequestBody(String jsonFileName, Class<T> clazz){
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
}
