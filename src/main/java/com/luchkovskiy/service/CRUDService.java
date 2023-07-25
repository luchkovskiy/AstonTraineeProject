package com.luchkovskiy.service;

import java.util.List;

public interface CRUDService<T> {

    T create(T object);

    T read(Long id);

    List<T> readAll();

    T update(T object);

    void delete(Long id);

}
