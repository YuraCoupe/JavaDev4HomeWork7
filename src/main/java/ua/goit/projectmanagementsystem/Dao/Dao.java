package ua.goit.projectmanagementsystem.Dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> findAll();

    Optional<T> findById(Integer id);

    T create(T entity);

    void update(T entity);

    void delete(T entity);
}

