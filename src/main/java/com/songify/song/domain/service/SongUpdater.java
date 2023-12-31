package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.repository.SongRepository;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class SongUpdater {

    private final SongRepository songRepository;
    private final SongProvider songProvider;

    public void updateById(Long id, Song songToPut) {
        songProvider.getById(id);
        songRepository.updateById(id, songToPut);
    }

    public Song partiallyUpdateById(Long id, PartiallyUpdateSongRequestDto partiallySongRequestDto) {
        Song song = songProvider.getById(id);
        String nameToUpdate = song.getName();
        String artistNameToUpdate = song.getArtist();
        if (partiallySongRequestDto.name() != null) {
            nameToUpdate = partiallySongRequestDto.name();
            log.info("New name is: {}", nameToUpdate);
        }
        if (partiallySongRequestDto.artistName() != null) {
            artistNameToUpdate = partiallySongRequestDto.artistName();
            log.info("New artist name is: {}", artistNameToUpdate);
        }
        Song songToUpdate = new Song(nameToUpdate, artistNameToUpdate);
        songRepository.updateById(id, songToUpdate);
        return songToUpdate;
    }
}