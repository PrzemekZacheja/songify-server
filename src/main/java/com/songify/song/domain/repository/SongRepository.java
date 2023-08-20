package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepository {

    Map<Integer, SongEntity> databaseInMemory = new HashMap<>(Map.of(
            1, new SongEntity("The Beatles", "Let it Be"),
            2, new SongEntity("The Beatles", "Hey Jude"),
            3, new SongEntity("The Beatles", "Sgt. Pepper's Lonely Hearts Club Band"),
            4, new SongEntity("The Beatles", "A Hard Day's Night")));


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

    public SongEntity put(Integer id, SongEntity songToPut) {
        databaseInMemory.put(id, songToPut);
        return songToPut;
    }
}