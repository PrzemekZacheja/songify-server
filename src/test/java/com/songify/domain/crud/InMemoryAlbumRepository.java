package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

class InMemoryAlbumRepository implements AlbumRepository {

    Map<Long, Album> db = new HashMap<>();
    AtomicLong id = new AtomicLong(0);

    @Override
    public void save(final Album album) {
        long id = this.id.getAndIncrement();
        album.setId(id);
        db.put(id, album);
    }

    @Override
    public Optional<AlbumInfo> findByIdAndSongs_IdAndArtists_Id(final Long id) {
        Album album = db.get(id);
        return Optional.of(new AlbumInfoTestImpl(album));
    }

    @Override
    public Set<Album> findAllAlbumsByArtistsId(final Long id) {
        return db.values()
                 .stream()
                 .filter(album -> album.getArtists()
                                           .stream()
                                           .anyMatch(artist -> artist.getId()
                                                                     .equals(id)))
                 .collect(Collectors.toSet());
    }

    @Override
    public void deleteByIdIn(final Collection<Long> ids) {
        ids.forEach(id -> db.remove(id));
    }

    @Override
    public Optional<Album> findById(final Long id) {
        return db.values()
                 .stream()
                 .filter(album -> album.getId()
                                           .equals(id))
                 .findFirst();
    }

    @Override
    public Set<Album> findAllAlbums(Pageable pageable) {
        return new HashSet<>(db.values());
    }

    @Override
    public void deleteById(final Long id) {
        db.remove(id);
    }

    @Override
    public Album updateTitleById(final String title, final Long id) {
        Album album = db.get(id);
        album.setTitle(title);
        return album;
    }
}