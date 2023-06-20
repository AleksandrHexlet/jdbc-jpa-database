package ru.itmo.database.patterns.dao;

import ru.itmo.database.patterns.Item;
import ru.itmo.database.patterns.dao.DAO;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryItemStore implements DAO<Item, String> {

    // работа с MAP
    private HashMap<String, Item> items = new HashMap<>();

    @Override
    public void insert(Item item) {

    }

    @Override
    public void update(Item item) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public Item selectByUnique(String s) {
        return null;
    }

    @Override
    public ArrayList<Item> selectAll() {
        return null;
    }
}
