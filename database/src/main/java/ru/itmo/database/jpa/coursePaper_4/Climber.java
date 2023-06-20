package ru.itmo.database.jpa.coursePaper_4;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Climber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String address;

    public Climber(String fullName, String address) {
        this.fullName = fullName;
        this.address = address;
    }

    public Climber() {

    }
}

