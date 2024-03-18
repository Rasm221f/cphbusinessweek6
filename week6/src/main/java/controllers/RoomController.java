package controllers;

import dao.RoomDAO;
import io.javalin.http.Handler;
import model.Room;

public class RoomController {
    private final RoomDAO roomDAO = new RoomDAO();

    public Handler getAllRooms = ctx -> {
        try {
            ctx.json(roomDAO.getAll());
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

    public Handler getRoomById = ctx -> {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Room room = roomDAO.getById(id);
            if (room != null) {
                ctx.json(room);
            } else {
                ctx.status(404).result("Room not found");
            }
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

    public Handler createRoom = ctx -> {
        try {
            Room room = ctx.bodyAsClass(Room.class);
            Room createdRoom = roomDAO.create(room);
            if (createdRoom != null) {
                ctx.status(201).json(createdRoom);
            } else {
                ctx.status(400).result("Could not create room");
            }
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };

    public Handler updateRoom = ctx -> {
        try {
            Room room = ctx.bodyAsClass(Room.class);
            Room updatedRoom = roomDAO.update(room);
            if (updatedRoom != null) {
                ctx.json(updatedRoom);
            } else {
                ctx.status(404).result("Room not found");
            }
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };
    public Handler deleteRoom = ctx -> {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            roomDAO.delete(id);
            ctx.status(204);
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    };
}