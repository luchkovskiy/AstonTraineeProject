package com.luchkovskiy.repository;

import java.util.List;

public interface CRUDRepository<T> {

    T create(T object);

    T read(Long id);

    List<T> readAll();

    T update(T object);

    void delete(Long id);

}
