package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatabaseRepository<T> {
    List<T> getAll();
    T getById(int id);
    void update(T object);
    void create(T object);
    void remove(T object);
    void removeById(int id);
}
