package ru.itmo.database.patterns.dao;

import ru.itmo.database.patterns.Item;

import java.util.ArrayList;

public interface DAO<T, PK> {
    void insert(T t);

    void update(T t);

    void delete(PK pk);

    T selectByUnique(PK pk);

    ArrayList<Item> selectAll();
}
