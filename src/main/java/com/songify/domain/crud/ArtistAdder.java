package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


@Service
@AllArgsConstructor
@Log4j2
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
        log.info("Saved artist {}", saved);
        return new ArtistDto(saved.getId(), saved.getName());
    }

    ArtistDto addArtistWithDefaultAlbumAndSongs(final ArtistRequestDto artistRequestDto) {
        String nameOfArtistToSave = artistRequestDto.name();
        Artist artist = saveArtistWithDefaultAlbumAndSongs(nameOfArtistToSave);
        log.info("Saved artist with default album and song {}", artist);
        return new ArtistDto(artist.getId(), artist.getName());
    }

    private Artist saveArtistWithDefaultAlbumAndSongs(final String nameOfArtistToSave) {
        Artist artist = new Artist(nameOfArtistToSave);

        String nameOfDefaultSong = "Default Song :" + UUID.randomUUID();
        SongDto addedSong = songAdder.addSong(new SongRequestDto(nameOfDefaultSong, Instant.now(), 1L, SongLanguage.ENGLISH));

        String nameDefaultOfAlbum = "Default Album : " + UUID.randomUUID();
        AlbumRequestDto albumRequestDto = new AlbumRequestDto(nameDefaultOfAlbum, Instant.now(), addedSong.id());
        albumAdder.addAlbumWithSong(albumRequestDto);

        return repository.save(artist);
    }
}