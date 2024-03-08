package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

class InMemoryArtistRepository implements ArtistRepository {

    Map<AtomicLong, Artist> artists = new HashMap<AtomicLong, Artist>();
    AtomicLong id = new AtomicLong(0L);

    @Override
    public Artist save(final Artist artist) {
        artists.put(id, artist);
        artist.setId(id.incrementAndGet());
        return artist;
    }

    @Override
    public Set<Artist> findAll(final Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Artist> findById(final Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(final Long id) {

    }
}