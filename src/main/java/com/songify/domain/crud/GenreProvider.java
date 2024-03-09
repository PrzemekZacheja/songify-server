package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
class GenreProvider {

    private final GenreRepository genreRepository;

    Set<GenreDto> findAll(final Pageable pageable) {
        return genreRepository.findAll(pageable)
                              .stream()
                              .map(GenreMapper::mapFromGenreToGenreDto)
                              .collect(Collectors.toSet());
    }
}