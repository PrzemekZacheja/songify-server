package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

class InMemorySongRepository implements SongRepository {
    @Override
    public Song save(final Song song) {
        return null;
    }

    @Override
    public List<Song> findAll(final Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Song> findById(final Long id) {
        return Optional.empty();
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