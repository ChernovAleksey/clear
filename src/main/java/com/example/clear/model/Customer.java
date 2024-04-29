package com.example.clear.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer extends AbstractEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "birthdate")
    private Date birthdate;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;

    public void setStringBirthdate(String dateline) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
        this.birthdate = format.parse(dateline);
    }
}
