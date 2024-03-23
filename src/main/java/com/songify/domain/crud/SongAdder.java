package com.songify.domain.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongAdder {

    private final SongRepository songRepository;
    private final AlbumProvider albumProvider;
    private final SongProvider songProvider;

    Song addSong(Song song) {
        Song savedToDatabase = songRepository.save(song);
        log.info("Song created: {}", savedToDatabase.toString());
        return savedToDatabase;
    }

    void addSongToAlbum(final Long idSong, final Long idAlbum) {
        Album album = albumProvider.findById(idAlbum);
        Song song = songProvider.findSongById(idSong);
        album.addSong(song);
        log.info("Song added to album: {}", album.toString());
    }
}