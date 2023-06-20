package ru.itmo.database.jpa.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import ru.itmo.database.jpa.entity.Book;
import ru.itmo.database.jpa.entity.BookIssuance;

public class Specifications {
    private Specifications() {}

    public static class BookSpecifications {
        private BookSpecifications() {}

        public static Specification<Book> bookByTitle(String title) {
            return (root, query, builder) -> builder.equal(root.get("title"), title);
        }

        public static Specification<Book> bookByCount(int numberOfBooks) {
            return (root, query, builder) -> builder.greaterThan(root.get("numberOfBooks"), numberOfBooks);
        }

        public static Specification<Book> booksWithoutIssuance(){
            return (root, query, builder) -> {
                Join<Book, BookIssuance> bookIssuanceJoin = root.join("issuances", JoinType.LEFT);
                return builder.isNull(bookIssuanceJoin.get("user"));
            };
        }
    }

}
