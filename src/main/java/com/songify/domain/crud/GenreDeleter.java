package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class GenreDeleter {

    private final GenreRepository genreRepository;
    private final SongProvider songProvider;
    private final SongDeleter songDeleter;

    void deleteById(final Long id) {
        List<Song> songByGenreId = songProvider.findSongByGenreId(id);
        songByGenreId
                .forEach(song -> songDeleter.deleteById(song.getId()));
        genreRepository.deleteById(id);
    }
}