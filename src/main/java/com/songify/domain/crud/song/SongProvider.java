package com.songify.domain.crud.song;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SongProvider {

    private final SongRepository songRepository;

     SongProvider(SongRepository songRepository) {
        this.songRepository = songRepository;
    }


     List<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

     Song getById(Long id) {
        return songRepository.findById(id)
                             .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

}