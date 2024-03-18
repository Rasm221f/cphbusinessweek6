package controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class RoomControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7000;
    }

    @Test
    public void testCreateRoom_ShouldReturnCreated() {
        // Assuming there's a Hotel with ID 1 to associate the room with
        RestAssured.given().contentType("application/json")
                .body("{\"hotelId\": 1, \"number\": 101, \"price\": \"$99\"}")
                .when().post("/rooms")
                .then().statusCode(201)
                .body("number", equalTo(101));
    }

    @Test
    public void testGetAllRooms_ShouldReturnNotEmpty() {
        RestAssured.when().get("/rooms")
                .then().statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    public void testGetRoomById_ShouldReturnRoom() {
        // Assuming there's a Room with ID 1
        RestAssured.when().get("/rooms/1")
                .then().statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void testUpdateRoom_ShouldReflectChanges() {
        // Assuming there's a Room with ID 1
        RestAssured.given().contentType("application/json")
                .body("{\"hotelId\": 1, \"number\": 102, \"price\": \"$199\"}")
                .when().put("/rooms/1")
                .then().statusCode(200)
                .body("price", equalTo("$199"));
    }

    @Test
    public void testDeleteRoom_ShouldReturnNoContent() {
        // Create a room to delete, assuming a Hotel with ID 1 exists
        int roomId = RestAssured.given().contentType("application/json")
                .body("{\"hotelId\": 1, \"number\": 103, \"price\": \"$299\"}")
                .post("/rooms").then().extract().path("id");

        // Now delete the room
        RestAssured.when().delete("/rooms/" + roomId)
                .then().statusCode(204);
    }
}
