package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreDeleter {

    private final GenreRepository genreRepository;

    Long deleteById(final Long id) {
        return genreRepository.deleteById(id);
    }
}