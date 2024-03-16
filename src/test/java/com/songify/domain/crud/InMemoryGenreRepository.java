package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryGenreRepository implements GenreRepository {

    Map<Long, Genre> db = new HashMap<>();
    AtomicLong id = new AtomicLong();

    @Override
    public Genre save(final Genre genre) {
        long id = this.id.getAndIncrement();
        genre.setId(id);
        db.put(id, genre);
        return genre;
    }

    @Override
    public Set<Genre> findAll(final Pageable pageable) {
        return new HashSet<>(db.values());
    }

    @Override
    public Long deleteById(final Long id) {
        return db.remove(id) != null ? id : -1L;
    }

    @Override
    public Optional<Genre> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }
}