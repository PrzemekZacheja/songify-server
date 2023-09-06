package com.songify.song.domain.service;

import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongDeleter {


    private final SongRepository songRepository;

    public SongDeleter(SongRepository songRepository, SongProvider songProvider) {
        this.songRepository = songRepository;
    }

    public void deleteById(Long id) {
        songRepository.deleteById(id);
        log.info("Song with id {} deleted", id);
    }
}