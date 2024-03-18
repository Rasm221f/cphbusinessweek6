package dao;

import model.Hotel;
import org.hibernate.Session;
import java.util.List;

public class HotelDAO extends AbstractDAO<Hotel> {

    public HotelDAO() {
        super(Hotel.class);
    }

    @Override
    public List<Hotel> getAll() {
        return executeInsideTransaction(session ->
                session.createQuery("from Hotel", Hotel.class).list());
    }

    @Override
    public Hotel getById(int id) {
        return executeInsideTransaction(session -> session.get(Hotel.class, id));
    }

    @Override
    public Hotel create(Hotel hotel) {
        executeInsideTransaction(session -> session.save(hotel));
        return hotel; // Hibernate populates the generated ID
    }

    @Override
    public Hotel update(Hotel hotel) {
        executeInsideTransaction(session -> {
            session.update(hotel);
            return null;
        });
        return hotel;
    }

    @Override
    public void delete(int id) {
        executeInsideTransaction(session -> {
            Hotel hotel = session.get(Hotel.class, id);
            if (hotel != null) session.delete(hotel);
            return null;
        });
    }
}
