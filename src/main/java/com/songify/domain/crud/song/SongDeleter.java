package com.songify.domain.crud.song;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
class SongDeleter {


    private final SongRepository songRepository;

     SongDeleter(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

     void deleteById(Long id) {
        songRepository.deleteById(id);
        log.info("Song with id {} deleted", id);
    }
}