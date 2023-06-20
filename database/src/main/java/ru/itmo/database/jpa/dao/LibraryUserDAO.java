package ru.itmo.database.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.itmo.database.jpa.entity.LibraryUser;

import java.util.List;

public class LibraryUserDAO extends AbstractDao<LibraryUser, String> {

    public LibraryUserDAO(EntityManager entityManager, Class<LibraryUser> clazz) {
        super(entityManager, clazz);
    }

    @Override
    public List<LibraryUser> selectAll() {
        List<LibraryUser> libraryUsers = null;
        /* 1. NAMED QUERY */
        TypedQuery<LibraryUser> namedQuery = entityManager.createNamedQuery("all", LibraryUser.class);
        libraryUsers = namedQuery.getResultList();

        /* 2. JPQL QUERY */
        TypedQuery<LibraryUser> jpqlQuery = entityManager.createQuery("SELECT lu FROM LibraryUser lu", LibraryUser.class);
        libraryUsers = jpqlQuery.getResultList();

        /* 3. NATIVE QUERY */
        Query nativeQuery = entityManager.createNativeQuery("SELECT * FROM tb_users", LibraryUser.class);
        libraryUsers = (List<LibraryUser>) nativeQuery.getResultList();

        /* 4. Criteria API */
        // criteriaBuilder - формирует SQL запросы / criteriaQuery - SQL запрос (аналог SQL строки)

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // тип данных результата
        CriteriaQuery<LibraryUser> criteriaQuery = criteriaBuilder.createQuery(LibraryUser.class);

        // откуда извлекаются данные
        Root<LibraryUser> root = criteriaQuery.from(LibraryUser.class); // FROM таблица

        criteriaQuery.select(root); // SELECT

        TypedQuery<LibraryUser> query = entityManager.createQuery(criteriaQuery);
        libraryUsers = query.getResultList();

        return libraryUsers;
    }
}
