package ru.itmo.database.jpa.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter

public class PublishingPrimaryKey implements Serializable { /* implements Serializable - обязательно */
    private String title;
    private City city;

    public PublishingPrimaryKey(String title, City city) {
        this.title = title;
        this.city = city;
    }

    public PublishingPrimaryKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishingPrimaryKey that = (PublishingPrimaryKey) o;
        return Objects.equals(title, that.title) && city == that.city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, city);
    }
}