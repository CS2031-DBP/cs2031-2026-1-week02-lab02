package org.lab.week02lab02.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(nullable = false,  length = 200)
    private String bio;

    Date birthDate;

    public Artist() {
    }

    public Artist(String name, String bio, Date birthDate) {
        this.name = name;
        this.bio = bio;
        this.birthDate = birthDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public Date getBirthDate() {
        return birthDate;
    }
}
