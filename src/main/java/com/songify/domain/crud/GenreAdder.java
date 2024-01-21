package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
class GenreAdder {

    private final GenreRepository genreRepository;

    GenreDto addGenre(final GenreRequestDto genreRequestDto) {
        Genre genre = new Genre(genreRequestDto.name());
        Genre saved = genreRepository.save(genre);
        return new GenreDto(saved.getId(), saved.getName());
    }
}