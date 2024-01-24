package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
class SongProvider {

    private final SongRepository songRepository;

    List<SongDto> findAll(Pageable pageable) {
        return songRepository.findAll(pageable)
                             .stream()
                             .map(SongMapper::mapFromSongToSongDto)
                             .toList();
    }

    SongDto findSongDtoById(Long id) {
        return songRepository.findById(id)
                             .map(SongMapper::mapFromSongToSongDto)
                             .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

    Song findSongById(Long id) {
        return songRepository.findById(id)
                             .orElseThrow(() -> new SongNotFoundException("Song with id " + id + " not found"));
    }

}