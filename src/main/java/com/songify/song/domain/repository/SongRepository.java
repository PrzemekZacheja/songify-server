package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SongRepository {

    public static final String THE_BEATLES = "The Beatles";

    final Map<Integer, SongEntity> databaseInMemory = new HashMap<>(Map.of(
            1, new SongEntity(THE_BEATLES, "Let it Be"),
            2, new SongEntity(THE_BEATLES, "Hey Jude"),
            3, new SongEntity(THE_BEATLES, "Sgt. Pepper's Lonely Hearts Club Band"),
            4, new SongEntity(THE_BEATLES, "A Hard Day's Night")));


    public SongEntity saveToDatabase(SongEntity songEntity) {
        int key = databaseInMemory.size() + 1;
        databaseInMemory.put(key, songEntity);
        return songEntity;
    }

    public Map<Integer, SongEntity> findAll() {
        return databaseInMemory;
    }

    public SongEntity getById(Integer id) {
        return databaseInMemory.get(id);
    }

    public SongEntity remove(Integer id) {
        return databaseInMemory.remove(id);
    }

    public void put(Integer id, SongEntity songToPut) {
        databaseInMemory.put(id, songToPut);
    }

    public Map<Integer, SongEntity> getLimitedSongs(Integer limitOfSongs) {
        return databaseInMemory.entrySet()
                .stream()
                .limit(limitOfSongs)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}