package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository repository;
    private final AlbumAdder albumAdder;
    private final SongAdder songAdder;

    ArtistDto addArtist(final ArtistRequestDto artistRequestDto) {
        String name = artistRequestDto.name();
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Artist name cannot be empty");
        }
        Artist artist = new Artist(name);
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

        String nameOfDefaultSong = "Default Song :" + UUID.randomUUID();
        Song addedSong = songAdder.addSong(new Song(nameOfDefaultSong));

        String nameDefaultOfAlbum = "Default Album : " + UUID.randomUUID();
        AlbumRequestDto albumRequestDto = new AlbumRequestDto(nameDefaultOfAlbum, Instant.now(), addedSong.getId());
        albumAdder.addAlbumWithSong(albumRequestDto);

        return repository.save(artist);
    }
}