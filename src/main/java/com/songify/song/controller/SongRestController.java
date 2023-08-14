package com.songify.song.controller;

import com.songify.song.dto.ErrorSongResponseDto;
import com.songify.song.dto.SongDto;
import com.songify.song.dto.SongRequestDto;
import com.songify.song.dto.SongResponseDto;
import com.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class SongRestController {

    public static final String SONG_NOT_FOUND = "Song not found";
    Map<Integer, SongDto> databaseInMemory = new HashMap<>(
            Map.of(1,
                   new SongDto("Song1"),
                   2,
                   new SongDto("Song2"),
                   3,
                   new SongDto("Song3"),
                   4,
                   new SongDto("Song4")));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer id) {
        if (id != null) {
            SongDto songDto = databaseInMemory.get(id);
            if (songDto == null) {
                throw new SongNotFoundException(SONG_NOT_FOUND);
            }
            SongResponseDto singleMapOfSongDto = new SongResponseDto(Map.of(id, songDto));
            return ResponseEntity.ok(singleMapOfSongDto);
        }
        return ResponseEntity.ok(new SongResponseDto(databaseInMemory));
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable("id") int id, @RequestHeader(required = false) String authorizationHeader) {
        if (authorizationHeader != null) {
            log.info("Authorization header: {}", authorizationHeader);
        }
        SongDto songDto = databaseInMemory.get(id);
        if (songDto == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        return ResponseEntity.ok(songDto);
    }

    @PostMapping("/songs")
    public ResponseEntity<SongDto> createSong(@RequestBody @Valid SongRequestDto songRequestDto) {
        int key = databaseInMemory.size() + 1;
        databaseInMemory.put(key, new SongDto(songRequestDto.songName()));
        log.info("Song created: {}", songRequestDto);
        return ResponseEntity.ok(databaseInMemory.get(key));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<ErrorSongResponseDto> deleteSongByPathVariable(@PathVariable("id") Integer id) {
        return getDeleteSongResponseDtoResponseEntity(id);
    }

    @DeleteMapping("/songs")
    public ResponseEntity<ErrorSongResponseDto> deleteSongByQueryParam(@RequestParam("id") Integer id) {
        return getDeleteSongResponseDtoResponseEntity(id);
    }

    private ResponseEntity<ErrorSongResponseDto> getDeleteSongResponseDtoResponseEntity(@RequestParam("id") Integer id) {
        SongDto songDto = databaseInMemory.get(id);
        if (songDto == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        databaseInMemory.remove(id);
        return ResponseEntity.ok(new ErrorSongResponseDto("Deleted song " + id, HttpStatus.OK));
    }
}