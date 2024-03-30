package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class ArtistInfoTestImpl implements AlbumInfo.ArtistInfo {

    private final Artist artist;

    @Override
    public Long getId() {
        return artist.getId();
    }

    @Override
    public String getName() {
        return artist.getName();
    }
}