package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Log4j2
@AllArgsConstructor
class SongDeleter {

    private final SongRepository songRepository;

    void deleteById(Long id) {
        songRepository.deleteById(id);
        log.info("Song with id {} deleted", id);
    }

    void deleteAllSongsById(final Set<Long> idsOfSongsToDelete) {
        songRepository.deleteSongsByIds(idsOfSongsToDelete);
    }
}