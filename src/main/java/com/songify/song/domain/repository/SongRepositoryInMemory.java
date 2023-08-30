package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepositoryInMemory implements SongRepository {

    public static final String THE_BEATLES = "The Beatles";

    final Map<Integer, Song> databaseInMemory = new HashMap<>(
            Map.of(1, new Song(THE_BEATLES, "Let it Be"),
                    2, new Song(THE_BEATLES, "Hey Jude"),
                    3, new Song(THE_BEATLES, "Sgt. Pepper's Lonely Hearts Club Band"),
                    4, new Song(THE_BEATLES, "A Hard Day's Night")));

    @Override
    public Song save(Song song) {
        int key = databaseInMemory.size() + 1;
        databaseInMemory.put(key, song);
        return song;
    }

    @Override
    public List<Song> findAll() {
        return databaseInMemory.values()
                               .stream()
                               .toList();
    }


    @Override
    public Song getById(Integer id) {
        return databaseInMemory.get(id);
    }

    @Override
    public Song remove(Integer id) {
        return databaseInMemory.remove(id);
    }

    @Override
    public void put(Integer id, Song songToPut) {
        databaseInMemory.put(id, songToPut);
    }

    @Override
    public List<Song> getLimitedSongs(Integer limitOfSongs) {
        return databaseInMemory.values()
                               .stream()
                               .limit(limitOfSongs)
                               .toList();
    }
}