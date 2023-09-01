package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongProvider {

    private final SongRepository songRepository;

    public SongProvider(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    public List<Song> findAll() {
        return songRepository.findAll();
    }

    public List<Song> getLimitedSongs(Integer limitOfSongs) {
        return songRepository.findAll().stream().limit(limitOfSongs).toList();
    }

    public Optional<Song> getById(Long id) {
        return songRepository.findById(id);
    }


//    public void put(Integer id, Song songToPut) {
//        songRepositoryInMemory.put(id, songToPut);
//    }
}