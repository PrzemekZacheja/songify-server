package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryGenreRepository implements GenreRepository {

    final Map<Long, Genre> db = new HashMap<>();
    final AtomicLong id = new AtomicLong();

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
    public void deleteById(final Long id) {
        db.remove(id);
    }

    @Override
    public Optional<Genre> findById(final Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public void updateNameById(final String name, final Long id) {
        db.get(id)
          .setName(name);
    }

}