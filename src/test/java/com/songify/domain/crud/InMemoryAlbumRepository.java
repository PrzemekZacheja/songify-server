package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

class InMemoryAlbumRepository implements AlbumRepository {
    @Override
    public void save(final Album album) {

    }

    @Override
    public Optional<AlbumInfo> findByIdAndSongs_IdAndArtists_Id(final Long id) {
        return Optional.empty();
    }

    @Override
    public Set<Album> findAllAlbumsByArtistsId(final Long id) {
        return null;
    }

    @Override
    public void deleteByIdIn(final Collection<Long> ids) {

    }

    @Override
    public Optional<Album> findById(final Long id) {
        return Optional.empty();
    }
}