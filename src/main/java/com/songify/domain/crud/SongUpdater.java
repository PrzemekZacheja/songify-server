package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
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

    SongDto updateById(Long id, Song songToPut) {
        SongDto byId = songProvider.findSongDtoById(id);
        songRepository.updateById(songToPut.getName(),
                                  songToPut.getReleaseDate(),
                                  songToPut.getDuration(),
                                  songToPut.getLanguage(),
                                  id);
        return byId;
    }

    SongDto partiallyUpdateById(Long id, SongDto songToPut) {
        Song song = songProvider.findSongById(id);

        Song songToUpdate = SongMapper.mapFromSongDtoToSong(songToPut);

        songRepository.updateById(songToUpdate.getName(),
                                  songToUpdate.getReleaseDate(),
                                  songToUpdate.getDuration(),
                                  songToUpdate.getLanguage(),
                                  id);
        return SongMapper.mapFromSongToSongDto(songToUpdate);
    }
}