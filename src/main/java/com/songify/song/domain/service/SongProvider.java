package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SongProvider {

    private final SongRepository songRepository;

    public SongProvider(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    public Map<Integer, SongEntity> findAll() {
        return songRepository.findAll();
    }

    public SongEntity getById(Integer id) {
        return songRepository.getById(id);
    }

    public SongEntity remove(Integer id) {
        return songRepository.remove(id);
    }

    public SongEntity put(Integer id, SongEntity songToPut) {
        return songRepository.put(id, songToPut);
    }
}