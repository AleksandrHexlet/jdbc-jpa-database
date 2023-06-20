package ru.itmo.database.patterns.dao;

import ru.itmo.database.patterns.Item;
import ru.itmo.database.patterns.dao.DAO;

import java.util.ArrayList;

public class DBItemStore implements DAO<Item, String> {

    // соединение с БД
    // SQL запросы

    public void insert(Item item) {

    }

    public void update(Item item) {

    }

    public void delete(String unique) {

    }

    public Item selectByUnique(String unique) {
        return null;
    }

    public ArrayList<Item> selectAll() {
        return null;
    }

}
