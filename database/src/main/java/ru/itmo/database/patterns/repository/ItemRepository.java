package ru.itmo.database.patterns.repository;

import ru.itmo.database.patterns.Item;
import ru.itmo.database.patterns.dao.DAO;

import java.util.List;

public class ItemRepository implements Repository<Item, String> {
    private final DAO<Item, String> dao;

    public ItemRepository(DAO<Item, String> dao) {
        this.dao = dao;
    }

    @Override
    public Item findByPK(String s) {
        return dao.selectByUnique(s);
    }

    @Override
    public List<Item> findAll() {
        return dao.selectAll();
    }

    @Override
    public void add(Item item) {
        dao.insert(item);
    }

    @Override
    public void update(Item item) {
        dao.update(item);
    }

    @Override
    public void remove(Item item) {
        dao.delete(item.getUnique());
    }

    /*
    @Override
    public List<Item> findByCondition(SearchCondition<Item> condition) {
        return null;
    }
    */
}