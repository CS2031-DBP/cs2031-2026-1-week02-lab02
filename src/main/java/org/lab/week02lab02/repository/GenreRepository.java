package org.lab.week02lab02.repository;

import org.lab.week02lab02.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
