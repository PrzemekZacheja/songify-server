package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface ArtistRepository extends Repository<Artist, Long> {
    Artist save(Artist artist);

    Set<Artist> findAll(final Pageable pageable);

    Optional<Artist> findById(Long id);

    @Modifying
    @Query("delete from Artist a where a.id = :id")
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("update Artist a set a.albums = ?1 where a.id = ?2")
    void updateAlbumsById(Set<Album> albums, Long id);
}