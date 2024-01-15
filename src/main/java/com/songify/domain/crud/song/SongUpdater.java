package com.songify.domain.crud.song;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Log4j2
class SongUpdater {

    private final SongRepository songRepository;
    private final SongProvider songProvider;

    SongDomainDto updateById(Long id, Song songToPut) {
        Song byId = songProvider.getById(id);
        songRepository.updateById(id, songToPut);
        return SongDomainMapper.mapFromSongToSongDomainDto(byId);
    }

    SongDomainDto partiallyUpdateById(Long id, SongDomainDto partiallySongRequestDto) {
        Song song = songProvider.getById(id);
        String nameToUpdate = song.getName();
        String artistNameToUpdate = song.getArtist();
        if (partiallySongRequestDto.name() != null) {
            nameToUpdate = partiallySongRequestDto.name();
            log.info("New name is: {}", nameToUpdate);
        }
        if (partiallySongRequestDto.artist() != null) {
            artistNameToUpdate = partiallySongRequestDto.artist();
            log.info("New artist name is: {}", artistNameToUpdate);
        }
        Song songToUpdate = new Song(nameToUpdate, artistNameToUpdate);
        songRepository.updateById(id, songToUpdate);
        return SongDomainMapper.mapFromSongToSongDomainDto(songToUpdate);
    }
}