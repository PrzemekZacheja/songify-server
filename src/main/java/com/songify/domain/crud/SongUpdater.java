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
        Song byId = songProvider.getById(id);
        songRepository.updateById(id, songToPut);
        return SongMapper.mapFromSongToSongDomainDto(byId);
    }

    SongDto partiallyUpdateById(Long id, SongDto partiallySongRequestDto) {
        Song song = songProvider.getById(id);
        String nameToUpdate = song.getName();
        if (partiallySongRequestDto.name() != null) {
            nameToUpdate = partiallySongRequestDto.name();
            log.info("New name is: {}", nameToUpdate);
        }
        Song songToUpdate = new Song(nameToUpdate);
        songRepository.updateById(id, songToUpdate);
        return SongMapper.mapFromSongToSongDomainDto(songToUpdate);
    }
}