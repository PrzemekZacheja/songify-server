package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SongRepository extends Repository<Song, Long> {

    Song save(Song song);

    @Query("select s from Song s")
    List<Song> findAll(Pageable pageable);

    @Query("select s from Song s where s.id = :id")
    Optional<Song> findById(Long id);

    @Modifying
    @Query("DELETE FROM Song s where s.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("delete from Song s where s.id in :ids")
    void deleteSongsByIds(Collection<Long> ids);

    @Modifying
    @Query("update Song s set s.name = ?1, s.releaseDate = ?2, s.duration = ?3, s.language = ?4 where s.id = ?5")
    void updateById(String name,
                   Instant releaseDate,
                   Long duration,
                   SongLanguage language,
                   Long id);

    List<Song> findByGenre_Id(Long id);
}