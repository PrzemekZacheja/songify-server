package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
class ArtistAssigner {

    private final ArtistProvider artistProvider;
    private final AlbumProvider albumProvider;
    private final ArtistRepository artistRepository;

    void addArtistToAlbum(final Long artistId, final Long albumId) {
        Artist artist = artistProvider.findById(artistId);
        Album album = albumProvider.findAlbumById(albumId);
        artist.addAlbum(album);
        album.addArtist(artist);
        log.info("Artist {} was added to album {}", artist.getName(), album.getTitle());
        Set<Album> albums = artist.getAlbums();
        artistRepository.updateAlbumsById(albums, artistId);
    }
}