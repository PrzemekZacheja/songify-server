package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryGenreRepository implements GenreRepository {

    Map<Long, Genre> genres = new HashMap<>();
    AtomicLong id = new AtomicLong();

    @Override
    public Genre save(final Genre genre) {
        long id = this.id.getAndIncrement();
        genre.setId(id);
        genres.put(id, genre);
        return genre;
    }

    @Override
    public Set<Genre> findAll(final Pageable pageable) {
        return new HashSet<>(genres.values());
    }

    @Override
    public Long deleteById(final Long id) {
        return genres.remove(id) != null ? id : -1L;
    }
}