package dao;


import java.util.List;

public interface IDAO<T> {
    List<T> getAll() throws Exception;
    T getById(int id) throws Exception;
    T create(T t) throws Exception;
    T update(T t) throws Exception;
    void delete(int id) throws Exception;
}