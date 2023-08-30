package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SongAdder {

    private final SongRepository SongRepositoryInMemory;

    public SongAdder(SongRepository songRepositoryInMemory) {
        SongRepositoryInMemory = songRepositoryInMemory;
    }

    public Song addSong(Song song) {
        Song savedToDatabase = SongRepositoryInMemory.save(song);
        log.info("Song created: {}", savedToDatabase);
        return savedToDatabase;
    }

}