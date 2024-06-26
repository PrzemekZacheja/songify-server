package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AlbumRepository extends Repository<Album, Long> {

    void save(Album album);

    @Query("""
           select a from Album a
           inner join fetch a.songs songs
           inner join fetch a.artists artists
           where a.id = :id""")
    Optional<AlbumInfo> findByIdAndSongs_IdAndArtists_Id(@Param("id") Long id);

    @Query("""
           select a from Album a\s
           inner join a.artists artists\s
           where artists.id = :id""")
    Set<Album> findAllAlbumsByArtistsId(@Param("id") Long id);

    @Modifying
    @Query("delete from Album a where a.id in :ids")
    void deleteByIdIn(Collection<Long> ids);

    Optional<Album> findById(Long id);

    Set<Album> findAllAlbums(final Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Album a where a.id = :id")
    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("update Album a set a.title = ?1 where a.id = ?2")
    Album updateTitleById(String title, Long id);

}