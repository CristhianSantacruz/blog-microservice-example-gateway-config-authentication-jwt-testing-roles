package com.klashz;

import com.klashz.dto.AuthRequest;
import com.klashz.model.UserEntity;
import com.klashz.utils.RoleUser;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(AuthenticationRest.class)
class AuthenticationRestTest {

    private static UserEntity userTest;
    private static UserEntity userTestAfterSave;
    private static AuthRequest authRequest;


    @BeforeAll
    public static void setup(){
        userTest = new UserEntity();
        userTest.setUsername("admin");
        userTest.setPassword("admin");
        userTest.setEmail("admin@admin.com");
        userTest.setRol(RoleUser.admin);

        authRequest = new AuthRequest(userTest.getUsername(),userTest.getPassword());
    }


    @Test
    @Order(2)
    void login() {
        given()
                .body(authRequest)
                .contentType(ContentType.JSON)
                .when().post("/login")
                .then().statusCode(200)
                .body("tokenGenerated",notNullValue());
    }

    @Test
    @Order(1)
    void register() {
        Response response  = given()
                .contentType(ContentType.JSON)
                .body(userTest)
                .when().post("/register")
                .then().statusCode(201)
                .body("id",notNullValue()).extract().response();

        userTestAfterSave = response.as(UserEntity.class);

    }
}