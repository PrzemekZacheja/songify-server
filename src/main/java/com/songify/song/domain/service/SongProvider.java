package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return songRepository.findAll()
                             .stream()
                             .limit(limitOfSongs)
                             .toList();
    }

    public Song getById(Long id) {
        return songRepository.findById(id)
                             .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

}