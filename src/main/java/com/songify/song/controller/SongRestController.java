package com.songify.song.controller;

import com.songify.song.dto.*;
import com.songify.song.dto.request.SongRequestDto;
import com.songify.song.dto.response.ErrorSongResponseDto;
import com.songify.song.dto.response.SingleSongResponseDto;
import com.songify.song.dto.response.SongResponseDto;
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
    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable("id") int id, @RequestHeader(required = false) String authorizationHeader) {
        if (authorizationHeader != null) {
            log.info("Authorization header: {}", authorizationHeader);
        }
        SongDto songDto = databaseInMemory.get(id);
        if (songDto == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        return ResponseEntity.ok(new SingleSongResponseDto(songDto.name()));
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> createSong(@RequestBody @Valid SongRequestDto songRequestDto) {
        int key = databaseInMemory.size() + 1;
        databaseInMemory.put(key, new SongDto(songRequestDto.songName()));
        log.info("Song created: {}", songRequestDto);
        return ResponseEntity.ok(new SingleSongResponseDto(songRequestDto.songName()));
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

    @PutMapping("/songs/")
    public ResponseEntity<SingleSongResponseDto> updateSong(@RequestBody @Valid SongRequestDto songRequestDto,
                                              @RequestParam Integer id) {
        SongDto songDto = databaseInMemory.get(id);
        if (songDto == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        databaseInMemory.put(id, new SongDto(songRequestDto.songName()));
        log.info("Song updated: {}", songRequestDto);
        return ResponseEntity.ok(new SingleSongResponseDto(songRequestDto.songName()));
    }
}