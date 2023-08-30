package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongProvider {

    private final SongRepository songRepositoryInMemory;

    public SongProvider(SongRepository songRepositoryInMemory) {
        this.songRepositoryInMemory = songRepositoryInMemory;
    }


    public List<Song> findAll() {
        return songRepositoryInMemory.findAll();
    }

    public List<Song> getLimitedSongs(Integer limitOfSongs) {
        return songRepositoryInMemory.findAll().stream().limit(limitOfSongs).toList();
    }

    public Song getById(Integer id) {
        return songRepositoryInMemory.getById(id);
    }

    public Song remove(Integer id) {
        return songRepositoryInMemory.remove(id);
    }

    public void put(Integer id, Song songToPut) {
        songRepositoryInMemory.put(id, songToPut);
    }
}