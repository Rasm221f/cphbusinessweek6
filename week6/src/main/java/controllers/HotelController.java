package controllers;

import dao.HotelDAO;
import io.javalin.http.Handler;
import model.Hotel;

public class HotelController {
    private final HotelDAO hotelDAO = new HotelDAO();

    public Handler getAllHotels = ctx -> {
        try {
            ctx.json(hotelDAO.getAll());
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

    public Handler getHotelById = ctx -> {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Hotel hotel = hotelDAO.getById(id);
            if (hotel != null) {
                ctx.json(hotel);
            } else {
                ctx.status(404).result("Hotel not found");
            }
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

    public Handler createHotel = ctx -> {
        try {
            Hotel hotel = ctx.bodyAsClass(Hotel.class);
            Hotel createdHotel = hotelDAO.create(hotel);
            if (createdHotel != null) {
                ctx.status(201).json(createdHotel);
            } else {
                ctx.status(400).result("Could not create hotel");
            }
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

    public Handler updateHotel = ctx -> {
        try {
            Hotel hotel = ctx.bodyAsClass(Hotel.class);
            Hotel updatedHotel = hotelDAO.update(hotel);
            if (updatedHotel != null) {
                ctx.json(updatedHotel);
            } else {
                ctx.status(404).result("Hotel not found");
            }
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

    public Handler deleteHotel = ctx -> {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            hotelDAO.delete(id);
            ctx.status(204);
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };
}