package org.lab.week02lab02.service;

import org.lab.week02lab02.model.Song;
import org.lab.week02lab02.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song save(org.lab.week02lab02.model.Song song) {
        if (song != null && song.getTitle() != null && !song.getTitle().isEmpty()) {
             return songRepository.save(song);
        } else {
            throw new IllegalArgumentException("Song title cannot be null or empty");
        }
    }

    public Song findById(long id) {
        return songRepository.findById(id).orElse(null);
    }

    public List<Song> findAll(){
        return songRepository.findAll();
    }

    public void deleteById(long id) {
        songRepository.deleteById(id);
    }
}
