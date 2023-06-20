package ru.itmo.database.patterns.repository;

import java.util.List;

public interface Repository<T, PK> {

    T findByPK(PK pk);

    List<T> findAll();

    void add(T t);

    void update(T t);

    void remove(T t);

    /* List<T> findByCondition(SearchCondition<T> condition); */
}
