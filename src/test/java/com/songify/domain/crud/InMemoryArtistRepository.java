package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryArtistRepository implements ArtistRepository {

    Map<Long, Artist> artists = new HashMap<>();
    AtomicLong id = new AtomicLong();

    @Override
    public Artist save(final Artist artist) {
        long id = this.id.getAndIncrement();
        artist.setId(id);
        artists.put(id, artist);
        return artist;
    }

    @Override
    public Set<Artist> findAll(final Pageable pageable) {
        return new HashSet<>(artists.values());
    }

    @Override
    public Optional<Artist> findById(final Long id) {
        return artists.values()
                      .stream()
                      .filter(artist -> artist.getId()
                                              .equals(id))
                      .findFirst();
    }

    @Override
    public void deleteById(final Long id) {
        artists.remove(id);
    }
}