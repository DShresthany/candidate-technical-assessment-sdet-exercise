package com.gaggle.sdetassessment.base;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Wrapper client for RestAssured client.
 */
public class RestClient {

    public static Response post(String uri, String payload, Integer expectedStatusCode) {
        return given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(uri)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract().response();
    }

    public static Response put(String uri, String payload, Integer expectedStatusCode) {
        return given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(uri)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract().response();
    }

    public static Response get(String uri, Integer expectedStatusCode) {
        return given().contentType(ContentType.JSON)
                .when()
                .get(uri)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract().response();
    }

    public static Response delete(String uri, Integer expectedStatusCode) {
        return given().contentType(ContentType.JSON)
                .when()
                .delete(uri)
                .then()
                .assertThat()
                .statusCode(expectedStatusCode)
                .extract().response();
    }
}
