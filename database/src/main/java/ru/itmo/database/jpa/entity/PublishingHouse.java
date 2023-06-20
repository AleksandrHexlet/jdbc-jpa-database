package ru.itmo.database.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;

@Getter

@Entity
@Table(name = "tb_publishing_houses")
@IdClass(PublishingPrimaryKey.class)
public class PublishingHouse {
    /*@EmbeddedId
    private PrimaryKey key;*/

    @Id
    private String title;

    @Enumerated(EnumType.STRING)
    @Id
    private City city;

    @OneToMany(mappedBy = "publishingHouse", cascade = CascadeType.ALL)
    private ArrayList<Book> books;

    public PublishingHouse(PublishingPrimaryKey key) {
        this.title = key.getTitle();
        this.city = key.getCity();
    }

    public PublishingHouse() {
    }

    /*@Embeddable
    public static class PrimaryKey implements Serializable { *//* implements Serializable - обязательно *//*
        static final long serialVersionUID = 1L;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private City city;
    }*/
}
