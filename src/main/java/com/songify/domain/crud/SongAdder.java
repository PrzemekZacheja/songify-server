package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongAdder {

    private static final String DEFAULT_GENRE_NAME = "default";
    private final SongRepository songRepository;
    private final AlbumProvider albumProvider;
    private final SongProvider songProvider;

    SongDto addSong(SongRequestDto songRequestDto) {
        Song song = SongMapper.mapFromSongRequestDtoToSong(songRequestDto);
        Song savedToDatabase = songRepository.save(song);
        if (savedToDatabase.getGenre() == null) {
            savedToDatabase.setGenre(new Genre(DEFAULT_GENRE_NAME));
        }
        log.info("Song created: {}", savedToDatabase.toString());
        return SongMapper.mapFromSongToSongDto(savedToDatabase);
    }

    void addSongToAlbum(final Long idSong, final Long idAlbum) {
        Album album = albumProvider.findById(idAlbum);
        Song song = songProvider.findSongById(idSong);
        album.addSong(song);
        log.info("Song added to album: {}", album.toString());
    }
}