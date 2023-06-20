package ru.itmo.database.jpa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.itmo.database.jpa.entity.Book;


public class BookDao extends AbstractDao<Book, String>{

    public BookDao(EntityManager entityManager, Class<Book> clazz) {
        super(entityManager, clazz);
    }

    public Book selectBookByName(String name){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // параметризуется типом, который этот запрос возвращает
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

        // блок FROM - корневой объект, указывает, откуда будут браться данные
        Root<Book> root = criteriaQuery.from(Book.class);

        // блок WHERE
        Predicate condition = criteriaBuilder.equal(root.get("name"), name);

        criteriaQuery.select(root).where(condition);

        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
        Book book = query.getSingleResult();

        return book;
    }
}
