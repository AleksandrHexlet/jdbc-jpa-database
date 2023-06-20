package ru.itmo.database.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter

@Entity
@Table(name = "books_users")
public class BookIssuance {
    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private LibraryUser user;

    @Column(nullable = false)
    private LocalDate issued;

    @Column(columnDefinition = "INTEGER DEFAULT 7")
    private int days;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookIssuance that = (BookIssuance) o;
        return days == that.days && Objects.equals(book, that.book) && Objects.equals(user, that.user) && Objects.equals(issued, that.issued);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, user, issued, days);
    }
}
