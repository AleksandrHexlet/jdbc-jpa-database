package ru.itmo.database.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;

@Getter

@Entity
@Table(name = "tb_authors")
public class Author extends Unique {
    @Column(nullable = false, length = 200)
    private String fullName;


    @ManyToMany // автор является владельцем связи авторы - книги
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private ArrayList<Book> books;
}
