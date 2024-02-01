package com.songify.domain.crud;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Log4j2
class SongDeleter {


    private final SongRepository songRepository;

    SongDeleter(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    void deleteById(Long id) {
        songRepository.deleteById(id);
    }

    void deleteAllSongsById(final Set<Long> idsOfSongsToDelete) {
        songRepository.deleteSongsByIds(idsOfSongsToDelete);
    }
}