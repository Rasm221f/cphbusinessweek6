package controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class HotelControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7000;
    }

    @Test
    public void getAllHotels_ShouldReturnNotEmpty() {
        given()
                .when().get("/hotels")
                .then().statusCode(200)
                .body("$", hasSize(greaterThan(0))); // Ensure there's at least one hotel
    }

    // Add more tests (getHotelById, createHotel, etc.) here...
}
