package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
class SongProvider {

    private final SongRepository songRepository;

    List<Song> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    Song getById(Long id) {
        return songRepository.findById(id)
                             .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

}