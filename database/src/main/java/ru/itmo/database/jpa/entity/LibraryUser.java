package ru.itmo.database.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter

@NamedNativeQueries({
        @NamedNativeQuery(name = "all", query = "SELECT * FROM tb_users", resultClass = LibraryUser.class),
})
@NamedQueries(
        @NamedQuery(name = "byPhone", query = "SELECT lu FROM LibraryUser lu WHERE lu.phone = :phone")
)

@Entity                                 /* columnList = "field01, field02" для составных индексов */
@Table(name = "tb_users", indexes = @Index(columnList = "phone", unique = true))
public class LibraryUser extends Unique {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, length = 12 /* unique = true - уникальный индекс */)
    private String phone;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "user")
    private ArrayList<BookIssuance> issuances;

    public LibraryUser(String code, String fullName, String phone, String address) {
        super(code);
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
}
