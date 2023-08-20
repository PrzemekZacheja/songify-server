package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SongAdder {

    private final SongRepository SongRepository;

    public SongAdder(com.songify.song.domain.repository.SongRepository songRepository) {
        SongRepository = songRepository;
    }

    public SongEntity addSong(SongEntity songEntity) {
        SongEntity savedToDatabase = SongRepository.saveToDatabase(songEntity);
        log.info("Song created: {}", savedToDatabase);
        return savedToDatabase;
    }

}