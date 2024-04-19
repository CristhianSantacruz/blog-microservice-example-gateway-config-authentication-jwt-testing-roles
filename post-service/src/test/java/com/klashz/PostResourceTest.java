package com.klashz;

import com.klashz.model.PostEntity;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.enterprise.context.Dependent;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    private static PostEntity postTest;
    private static PostEntity afterSavePost;
    private static String testToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJqd3Qtc2VydmljZSIsInN1YiI6ImtsYXNoejEyMyIsImdyb3VwcyI6WyJhZG1pbiJdLCJleHAiOjE3MTM0NDg0MzU0MzIsImlhdCI6MTcxMzQ0ODQzMSwianRpIjoiYWMyMjA4M2ItZTFjMy00ZjFiLWJjYzMtZDVlZmQ2ODIzNzY2In0.pBG0jbGHiiTaoucdtHR61ZG7s3yAP813jCsSif-gCIqd6D-b3IWSkoeDcGQW3FcOsw3nWusqArAx8MYIL3vH2S30cbsqT8bitKEEMmYf2N4BvwWB-atLaFxbyE_dQuTky1Z2EBVLlaaAJpQQrGje0p1vFAnE6yTCQt6l5HrrtPsNC8V0_8OUum9P1zAraPPNV8f9XEvPvYCNC8C9PUxvNIoCEAC0BGHRVqEwqkuUWuUqfH8os2vt5A-4orCAl4YXvXQhIOhw7NKXEa1NaZ9vAZZcgS8GCdUWtPneM48ssjjmGXD2-93TeCoydsr0Cj7arD_tEzNeL2S8qvQGE-livQ";



    @BeforeAll
    public static void setup() {
        postTest = new PostEntity();
        postTest.description = "Quarkus is a framework...";
        postTest.title = "Quarkus";
        postTest.imageUrl = "http://image.example.com";
        postTest.tags = List.of("string","kotlin");

    }
    @Test
    @Order(1)
    void savePost(){
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + testToken)
                .body(postTest)
                .when()
                .post("/save")
                .then()
                .statusCode(201)
                .body("id", notNullValue()) // Asegúrate de que el ID no sea nulo
                .extract()
                .response();

        // Recupera la ID del post de la respuesta
        String postId = response.jsonPath().getString("id");
        System.out.println("ID del post creado: " + postId);
        postTest.id = postId;
        afterSavePost = response.as(PostEntity.class);
        System.out.println("Post desspues de haberse guardado"+afterSavePost);
    }

    @Test
    @Order(2)
    void getAllPost(){given().when().get("/all").then().statusCode(200);}

    @Test
    @Order(3)
    void updatePost(){
        afterSavePost.description ="quarkus";
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + testToken)
                .body(afterSavePost)
                .when().put("/update")
                .then().statusCode(200)
                .body("id", notNullValue())
                .body("description",is("quarkus"));
    }

    @Test
    @Order(4)
    void updateLikeCount(){
        given()
                .when()
                .pathParam("id",afterSavePost.id)
                .put("/update/{id}/like")
                .then().statusCode(200);

    }
    @Test
    @Order(5)
    void getPostById(){
        given()
                .pathParam("id",afterSavePost.id)
                .when().get("/{id}")
                .then()
                .statusCode(200)
                .body("id",notNullValue())
                .body("title",is("Quarkus"))
                .body("viewCount",is(1))
                .body("likeCount",is(1))
                .body("description",is("quarkus"));
    }
    @Test
    @Order(6)
    void deletePostById(){
        given().header("Authorization", "Bearer " + testToken)
                .pathParam("id",afterSavePost.id)
                .when().delete("/delete/{id}")
                .then().statusCode(200);
    }


}