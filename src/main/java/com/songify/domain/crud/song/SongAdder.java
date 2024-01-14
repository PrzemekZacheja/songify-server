package com.songify.domain.crud.song;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
@Transactional
public class SongAdder {

    private final SongRepository songRepository;

    public Song addSong(Song song) {
        Song savedToDatabase = songRepository.save(song);
        log.info("Song created: {}", savedToDatabase.toString());
        return savedToDatabase;
    }

}