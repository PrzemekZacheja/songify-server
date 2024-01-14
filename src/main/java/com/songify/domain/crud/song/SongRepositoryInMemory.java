package com.songify.domain.crud.song;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SongRepositoryInMemory implements SongRepository {

    public static final String THE_BEATLES = "The Beatles";

    final Map<Long, Song> databaseInMemory = new HashMap<>(
            Map.of(1L, new Song(THE_BEATLES, "Let it Be"),
                    2L, new Song(THE_BEATLES, "Hey Jude"),
                    3L, new Song(THE_BEATLES, "Sgt. Pepper's Lonely Hearts Club Band"),
                    4L, new Song(THE_BEATLES, "A Hard Day's Night")));

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
    public void updateById(Long id, Song song) {
        databaseInMemory.put(id, song);
    }

    //    @Override
//    public List<Song> getLimitedSongs(Integer limitOfSongs) {
//        return databaseInMemory.values()
//                               .stream()
//                               .limit(limitOfSongs)
//                               .toList();
//    }
}