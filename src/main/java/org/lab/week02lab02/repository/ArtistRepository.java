package org.lab.week02lab02.repository;

import org.lab.week02lab02.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
