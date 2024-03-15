package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

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
           select a from Album a 
           inner join a.artists artists 
           where artists.id = :id""")
    Set<Album> findAllAlbumsByArtistsId(@Param("id") Long id);

    @Modifying
    @Query("delete from Album a where a.id in :ids")
    void deleteByIdIn(Collection<Long> ids);

    Optional<Album> findById(Long id);

    Set<Album> findAllAlbums();
}