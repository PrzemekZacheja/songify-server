package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class InMemoryGenreRepository implements GenreRepository {

    Map<String, Genre> genres = new HashMap<>();
    Long id = 1L;

    @Override
    public Genre save(final Genre genre) {
        genre.setId(id++);
        genres.put(genre.getName(), genre);
        return genre;
    }

    @Override
    public Set<Genre> findAll(final Pageable pageable) {
        return new HashSet<>(genres.values());
    }
}