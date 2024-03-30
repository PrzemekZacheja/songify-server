package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
class GenreDeleter {

    private final GenreRepository genreRepository;
    private final SongProvider songProvider;
    private final SongDeleter songDeleter;

    void deleteById(final Long id) {
        List<SongDto> songByGenreId = songProvider.findSongsByGenreId(id);
        songByGenreId
                .forEach(song -> songDeleter.deleteById(song.id()));
        log.info("Songs by genre id {} were deleted", id);
        genreRepository.deleteById(id);
        log.info("Genre with id {} was deleted", id);
    }
}