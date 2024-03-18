package controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RoomControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7000; // Match this with your app's running port
    }

    @Test
    public void getAllRooms_ShouldReturnNotEmpty() {
        given()
                .when().get("/rooms")
                .then().statusCode(200)
                .body("$", hasSize(greaterThan(0))); // Assumes at least one room exists
    }

    // Further tests for other endpoints...
}
