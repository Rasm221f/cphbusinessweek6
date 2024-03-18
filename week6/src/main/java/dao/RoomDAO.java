package dao;

import model.Room;
import java.util.List;

public class RoomDAO extends AbstractDAO<Room> {

    public RoomDAO() {
        super(Room.class);
    }

    @Override
    public List<Room> getAll() {
        return executeInsideTransaction(session ->
                session.createQuery("from Room", Room.class).list());
    }

    @Override
    public Room getById(int id) {
        return executeInsideTransaction(session -> session.get(Room.class, id));
    }

    @Override
    public Room create(Room room) {
        executeInsideTransaction(session -> session.save(room));
        return room; // Hibernate populates the generated ID
    }

    @Override
    public Room update(Room room) {
        executeInsideTransaction(session -> {
            session.update(room);
            return null;
        });
        return room;
    }

    @Override
    public void delete(int id) {
        executeInsideTransaction(session -> {
            Room room = session.get(Room.class, id);
            if (room != null) session.delete(room);
            return null;
        });
    }
}
