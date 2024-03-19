package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface GenreRepository extends Repository<Genre, Long> {

    Genre save(Genre genre);

    Set<Genre> findAll(Pageable pageable);

    Long deleteById(Long id);

    Optional<Genre> findById(Long id);

    @Transactional
    @Modifying
    @Query("update Genre g set g.name = ?1 where g.id = ?2")
    void updateNameById(String name, Long id);
}