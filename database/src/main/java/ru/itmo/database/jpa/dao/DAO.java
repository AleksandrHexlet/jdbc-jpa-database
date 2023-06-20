package ru.itmo.database.jpa.dao;

import ru.itmo.database.jpa.specification.Specification;

import java.util.List;

public interface DAO<T, PK> {
    void create(T t);
    void delete(PK pk);
    void update(T t);
    List<T> selectAll();
    T selectByPK(PK pk);
    List<T> selectAllByCondition(Specification<T> specification);
    T selectOneByCondition(Specification<T> specification);
}
