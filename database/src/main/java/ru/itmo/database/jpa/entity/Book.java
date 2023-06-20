package ru.itmo.database.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;

@Getter

@Entity
@Table(name = "tb_books")
public class Book extends Unique {
    @Column(nullable = false)
    private String title;

    @Column(name = "published_year", nullable = false)
    private int published;

    @Column(columnDefinition = "INTEGER DEFAULT -1")
    private int price;

    @Column(nullable = false)
    private int numberOfBooks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publishing_title", referencedColumnName = "title", nullable = false)
    @JoinColumn(name = "publishing_city", referencedColumnName = "city", nullable = false)
    private PublishingHouse publishingHouse;

    @ManyToMany(mappedBy = "books", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ArrayList<Author> authors;

    @OneToMany(mappedBy = "book")
    private ArrayList<BookIssuance> issuances;

}
