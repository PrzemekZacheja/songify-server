package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistAssigner {

    private final ArtistProvider artistProvider;
    private final AlbumProvider albumProvider;

    void addArtistToAlbum(final Long artistId, final Long albumId) {
        Artist artist = artistProvider.findById(artistId);
        Album album = albumProvider.findById(albumId);
        artist.addAlbum(album);
    }
}