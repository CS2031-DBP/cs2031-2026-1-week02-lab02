package org.lab.week02lab02.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Album {
    @Id
    @GeneratedValue
    private long id;

    private String title;
    private Date releaseDate;
    private String label;

    public Album() {
    }

    public Album(String title, Date releaseDate, String label) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.label = label;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
