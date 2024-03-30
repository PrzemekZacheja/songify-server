package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Log4j2
class SongUpdater {

    private final SongRepository songRepository;
    private final SongProvider songProvider;

    SongDto updateById(Long id, SongRequestDto songToPut) {
        Song song = songRepository.updateById(songToPut.name(),
                                              songToPut.releaseDate(),
                                              songToPut.duration(),
                                              songToPut.songLanguage(),
                                              id);
        log.info("Song with id: {} was updated", id);
        return SongMapper.mapFromSongToSongDto(song);
    }

    SongDto updateNameById(Long id, final String songNameEdit) {
        Song song = songProvider.findSongById(id);
        song.setName(songNameEdit);
        songRepository.save(song);
        log.info("Song name with id: {} was updated", id);
        return SongMapper.mapFromSongToSongDto(song);
    }

    SongDto updateDurationById(final Long id, final long duration) {
        Song song = songProvider.findSongById(id);
        song.setDuration(duration);
        songRepository.save(song);
        log.info("Song duration with id: {} was updated", id);
        return SongMapper.mapFromSongToSongDto(song);
    }
}