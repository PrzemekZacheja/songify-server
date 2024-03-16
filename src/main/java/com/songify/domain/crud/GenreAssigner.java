package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreAssigner {

    private final SongProvider songProvider;
    private final GenreProvider genreProvider;

    void addGenreToSong(final Long genreId, final Long songId) {
        Song song = songProvider.findSongById(songId);
        Genre genre = genreProvider.findGenreById(genreId);
        song.setGenre(genre);
    }
}