package com.songify.domain.crud.song;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongProvider {

    private final SongRepository songRepository;

    public SongProvider(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


    public List<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    public Song getById(Long id) {
        return songRepository.findById(id)
                             .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

}