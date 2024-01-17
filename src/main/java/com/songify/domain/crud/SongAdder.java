package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
class SongAdder {

    private final SongRepository songRepository;

    Song addSong(Song song) {
        Song savedToDatabase = songRepository.save(song);
        log.info("Song created: {}", savedToDatabase.toString());
        return savedToDatabase;
    }

}