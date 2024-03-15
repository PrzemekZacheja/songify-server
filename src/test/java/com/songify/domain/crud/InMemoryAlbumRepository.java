package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

class InMemoryAlbumRepository implements AlbumRepository {

    Map<Long, Album> albums = new HashMap<>();
    AtomicLong id = new AtomicLong(0);

    @Override
    public void save(final Album album) {
        long id = this.id.getAndIncrement();
        album.setId(id);
        albums.put(id, album);
    }

    @Override
    public Optional<AlbumInfo> findByIdAndSongs_IdAndArtists_Id(final Long id) {
        Album album = albums.get(id);
        return Optional.of(new AlbumInfoTestImpl(album));
    }

    @Override
    public Set<Album> findAllAlbumsByArtistsId(final Long id) {
        return albums.values()
                     .stream()
                     .filter(album -> album.getArtists()
                                           .stream()
                                           .anyMatch(artist -> artist.getId()
                                                                     .equals(id)))
                     .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIdIn(final Collection<Long> ids) {

    }

    @Override
    public Optional<Album> findById(final Long id) {
        return albums.values()
                     .stream()
                     .filter(album -> album.getId()
                                           .equals(id))
                     .findFirst();
    }

    @Override
    public Set<Album> findAllAlbums() {
        return new HashSet<>(albums.values());
    }
}