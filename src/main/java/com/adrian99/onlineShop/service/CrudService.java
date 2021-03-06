package com.adrian99.onlineShop.service;

public interface CrudService<T, ID> {
    Iterable<T> findAll();

    T findById(ID id);

    T save(T object);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    void deleteById(ID id);

    void deleteAllById(Iterable<? extends ID> ids);
}
