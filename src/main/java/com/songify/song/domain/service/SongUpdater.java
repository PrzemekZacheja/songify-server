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
        Song song = songProvider.getById(id);
        song.setName(songToPut.getName());
        song.setArtist(songToPut.getArtist());
    }

    public Song partiallyUpdateById(Long id, PartiallyUpdateSongRequestDto partiallySongRequestDto) {
        Song song = songProvider.getById(id);
        if (partiallySongRequestDto.name() != null) {
            song.setName(partiallySongRequestDto.name());
            log.info("New name is: {}", partiallySongRequestDto.name());
        }
        if (partiallySongRequestDto.artistName() != null) {
            song.setArtist(partiallySongRequestDto.artistName());
            log.info("New artist is: {}", partiallySongRequestDto.artistName());
        }
        return song;
    }
}