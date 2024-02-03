package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository repository;

    ArtistDto addArtist(final ArtistRequestDto artistRequestDto) {
        Artist artist = new Artist(artistRequestDto.name());
        Artist saved = repository.save(artist);
        return new ArtistDto(saved.getId(), saved.getName());
    }

    ArtistDto addArtistWithDefaultAlbumAndSongs(final ArtistRequestDto artistRequestDto) {
        String nameOfArtistToSave = artistRequestDto.name();
        Artist artist = saveArtistWithDefaultAlbumAndSongs(nameOfArtistToSave);
        return new ArtistDto(artist.getId(), artist.getName());
    }

    private Artist saveArtistWithDefaultAlbumAndSongs(final String nameOfArtistToSave) {
        Artist artist = new Artist(nameOfArtistToSave);

        String nameDefaultOfAlbum = "Default album : " + UUID.randomUUID();
        Album album = new Album(nameDefaultOfAlbum);
        Song song = new Song("Default Song:" + UUID.randomUUID());
        album.addSong(song);
        artist.addAlbum(album);

        return repository.save(artist);
    }
}