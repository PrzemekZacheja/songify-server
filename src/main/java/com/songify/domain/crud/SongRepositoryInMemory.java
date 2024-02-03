package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("ClassEscapesDefinedScope")
public class SongRepositoryInMemory implements SongRepository {


    final Map<Long, Song> databaseInMemory = new HashMap<>(
            Map.of(1L, new Song("Let it Be"),
                   2L, new Song("Hey Jude"),
                   3L, new Song("Sgt. Pepper's Lonely Hearts Club Band"),
                   4L, new Song("A Hard Day's Night")));

    @Override
    public Song save(Song song) {
        long key = databaseInMemory.size() + 1;
        databaseInMemory.put(key, song);
        return song;
    }

    @Override
    public List<Song> findAll(Pageable pageable) {
        return databaseInMemory.values()
                               .stream()
                               .toList();
    }

    @Override
    public Optional<Song> findById(Long id) {
        return Optional.ofNullable(databaseInMemory.get(id));
    }

    @Override
    public void deleteById(Long id) {
        databaseInMemory.remove(id);
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

    //    @Override
//    public List<Song> getLimitedSongs(Integer limitOfSongs) {
//        return databaseInMemory.values()
//                               .stream()
//                               .limit(limitOfSongs)
//                               .toList();
//    }
}