package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("ClassEscapesDefinedScope")
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
    @Query("update Song s set s.name = :#{#song.name} where s.id = :id")
    void updateById(@Param("id") Long id, Song song);

    @Modifying
    @Query("delete from Song s where s.id in :ids")
    void deleteSongsByIds(Collection<Long> ids);

}