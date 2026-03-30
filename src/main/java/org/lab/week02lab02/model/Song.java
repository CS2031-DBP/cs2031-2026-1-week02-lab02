package org.lab.week02lab02.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Song {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    private Date releaseDate;
    private Integer duration;

    // Relationships
    @OneToMany
    private List<Album> albums = new ArrayList<>();

    @ManyToOne
    private Genre genre;

    @ManyToMany
    private List<Artist> artists = new ArrayList<>();


    public Song() {
    }

    public Song(String title, Date releaseDate, Integer duration) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
