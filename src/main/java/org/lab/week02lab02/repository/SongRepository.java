package org.lab.week02lab02.repository;

import org.lab.week02lab02.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
