package controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class HotelControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7000;
    }

    @Test
    public void testCreateHotel_ShouldReturnCreated() {
        RestAssured.given().contentType("application/json")
                .body("{\"name\": \"Test Hotel\", \"address\": \"123 Test Ave\", \"rooms\": \"[]\"}")
                .when().post("/hotels")
                .then().statusCode(201)
                .body("name", equalTo("Test Hotel"));
    }

    @Test
    public void testGetAllHotels_ShouldReturnNotEmpty() {
        RestAssured.when().get("/hotels")
                .then().statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    public void testGetHotelById_ShouldReturnHotel() {
        // Assuming there's a Hotel with ID 1
        RestAssured.when().get("/hotels/1")
                .then().statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void testUpdateHotel_ShouldReflectChanges() {
        // Assuming there's a Hotel with ID 1
        RestAssured.given().contentType("application/json")
                .body("{\"name\": \"Updated Test Hotel\", \"address\": \"123 Test Blvd\", \"rooms\": \"[]\"}")
                .when().put("/hotels/1")
                .then().statusCode(200)
                .body("name", equalTo("Updated Test Hotel"));
    }

    @Test
    public void testDeleteHotel_ShouldReturnNoContent() {
        // Create a hotel to delete
        int hotelId = RestAssured.given().contentType("application/json")
                .body("{\"name\": \"Delete Test Hotel\", \"address\": \"123 Delete Ave\", \"rooms\": \"[]\"}")
                .post("/hotels").then().extract().path("id");

        // Now delete the hotel
        RestAssured.when().delete("/hotels/" + hotelId)
                .then().statusCode(204);
    }
}
