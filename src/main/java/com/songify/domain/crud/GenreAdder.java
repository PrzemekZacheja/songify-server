package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j2
class GenreAdder {

    private final GenreRepository genreRepository;

    GenreDto addGenre(final GenreRequestDto genreRequestDto) {
        if (genreRequestDto.name()
                           .isEmpty()) {
            throw new IllegalArgumentException("Genre name cannot be empty");
        }
        Genre genre = new Genre(genreRequestDto.name());
        Genre saved = genreRepository.save(genre);
        log.info("Genre {} added", saved.getName());
        return new GenreDto(saved.getId(), saved.getName());
    }
}