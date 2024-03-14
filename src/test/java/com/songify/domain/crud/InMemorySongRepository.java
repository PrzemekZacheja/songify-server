package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

class InMemorySongRepository implements SongRepository {

    Map<Long, Song> songs = new HashMap<>();
    AtomicLong id = new AtomicLong();

    @Override
    public Song save(final Song song) {
        long id = this.id.getAndIncrement();
        song.setId(id);
        songs.put(song.getId(), song);
        return song;
    }

    @Override
    public List<Song> findAll(final Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Song> findById(final Long id) {
        return Optional.ofNullable(songs.get(id));
    }

    @Override
    public void deleteById(final Long id) {

    }

    @Override
    public void deleteSongsByIds(final Collection<Long> ids) {

    }

    @Override
    public int updateById(final String name,
                          final Instant releaseDate,
                          final Long duration,
                          final SongLanguage language,
                          final Long id) {
        return 0;
    }
}