package org.lab.week02lab02.controller;

import org.lab.week02lab02.model.Song;
import org.lab.week02lab02.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/new")
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song savedSong = songService.save(song);
        return ResponseEntity.ok(savedSong);
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        java.util.List<Song> songs = songService.findAll();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable long id) {
        Song song = songService.findById(id);
        if (song != null) {
            return ResponseEntity.ok(song);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSong(@RequestParam("id") long id) {
        songService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
