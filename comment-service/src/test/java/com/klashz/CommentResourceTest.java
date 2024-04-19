package com.klashz;

import com.klashz.model.CommentEntity;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(CommentWiremock.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CommentResourceTest {


    private static CommentEntity commentTest;
    private static String idPostExampleTest = "bd4b8cfa-822a-4e6c-bcd9-c0cd23eb6ffc";
    private static CommentEntity commentGenerated;
    private static String testToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJqd3Qtc2VydmljZSIsInN1YiI6ImtsYXNoejEyMyIsImdyb3VwcyI6WyJhZG1pbiJdLCJleHAiOjE3MTM0NDg0MzU0MzIsImlhdCI6MTcxMzQ0ODQzMSwianRpIjoiYWMyMjA4M2ItZTFjMy00ZjFiLWJjYzMtZDVlZmQ2ODIzNzY2In0.pBG0jbGHiiTaoucdtHR61ZG7s3yAP813jCsSif-gCIqd6D-b3IWSkoeDcGQW3FcOsw3nWusqArAx8MYIL3vH2S30cbsqT8bitKEEMmYf2N4BvwWB-atLaFxbyE_dQuTky1Z2EBVLlaaAJpQQrGje0p1vFAnE6yTCQt6l5HrrtPsNC8V0_8OUum9P1zAraPPNV8f9XEvPvYCNC8C9PUxvNIoCEAC0BGHRVqEwqkuUWuUqfH8os2vt5A-4orCAl4YXvXQhIOhw7NKXEa1NaZ9vAZZcgS8GCdUWtPneM48ssjjmGXD2-93TeCoydsr0Cj7arD_tEzNeL2S8qvQGE-livQ";

    //usar wiremock
    @BeforeAll
    public static void setup() {
        commentTest = new CommentEntity();
        commentTest.description = "Quarkus Learning Test";
        commentTest.idPost = idPostExampleTest;
    }

    @Test
    @Order(1)
    void saveComment(){
       Response response =  given()
               .body(commentTest).contentType(ContentType.JSON)
               .header("Authorization", "Bearer " + testToken)
               .when().post("comment/save")
               .then().statusCode(201)
               .body("id",notNullValue()).extract().response();
       commentGenerated = response.as(CommentEntity.class);

    }
    @Test
    @Order(2)
    void getCommentById(){
        given().pathParam("id",commentGenerated.id)
                .when().get("comment/{id}")
                .then().statusCode(200)
                .body("idPost",is(idPostExampleTest));
    }

    @Test
    @Order(3)
    void getAllCommentByPostId(){  //ubiar los poost como listas
        given().pathParam("idPost",idPostExampleTest)
                .when().get("comment/post/{idPost}")
                .then().statusCode(200);
    }

    @Test
    @Order(4)
    void updateComment(){
        given()
                .body(commentGenerated).contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + testToken)
                .when().put("comment/update")
                .then().statusCode(200)
                .body("idPost",is(idPostExampleTest));
    }

    @Test
    @Order(5)
    void deleteCommentById(){
        given().header("Authorization", "Bearer " + testToken)
                .pathParam("id",commentGenerated.id)
                .when().delete("comment/delete/{id}")
                .then().statusCode(200);
    }

}