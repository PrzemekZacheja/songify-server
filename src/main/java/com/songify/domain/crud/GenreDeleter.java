package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
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
        List<SongDto> songByGenreId = songProvider.findSongsByGenreId(id);
        songByGenreId
                .forEach(song -> songDeleter.deleteById(song.id()));
        genreRepository.deleteById(id);
    }
}