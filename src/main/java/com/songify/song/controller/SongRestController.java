package com.songify.song.controller;

import com.songify.song.dto.SongEntity;
import com.songify.song.dto.request.PartiallySongRequestDto;
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
    Map<Integer, SongEntity> databaseInMemory = new HashMap<>(
            Map.of(1, new SongEntity("The Beatles", "Let it Be"),
                   2, new SongEntity("The Beatles", "Hey Jude"),
                   3, new SongEntity("The Beatles", "Sgt. Pepper's Lonely Hearts Club Band"),
                   4, new SongEntity("The Beatles", "A Hard Day's Night")));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer id) {
        if (id != null) {
            SongEntity songEntity = databaseInMemory.get(id);
            if (songEntity == null) {
                throw new SongNotFoundException(SONG_NOT_FOUND);
            }
            SongResponseDto singleMapOfSongDto = new SongResponseDto(Map.of(id, songEntity));
            log.info("Song found: {}", singleMapOfSongDto);
            return ResponseEntity.ok(singleMapOfSongDto);
        }
        log.info("All songs: {}", databaseInMemory);
        return ResponseEntity.ok(new SongResponseDto(databaseInMemory));
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable("id") int id, @RequestHeader(required = false) String authorizationHeader) {
        if (authorizationHeader != null) {
            log.info("Authorization header: {}", authorizationHeader);
        }
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        log.info("Song found: {}", songEntity);
        return ResponseEntity.ok(new SingleSongResponseDto(songEntity.name(), songEntity.artistName()));
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> createSong(@RequestBody @Valid SongRequestDto songRequestDto) {
        int key = databaseInMemory.size() + 1;
        databaseInMemory.put(key, new SongEntity(songRequestDto.songName(), songRequestDto.artistName()));
        log.info("Song created: {}", songRequestDto);
        return ResponseEntity.ok(new SingleSongResponseDto(songRequestDto.songName(), songRequestDto.artistName()));
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
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        databaseInMemory.remove(id);
        log.info("Song deleted: {}", id);
        return ResponseEntity.ok(new ErrorSongResponseDto("Deleted song " + id, HttpStatus.OK));
    }

    @PutMapping("/songs/")
    public ResponseEntity<SingleSongResponseDto> updateSong(@RequestBody @Valid SongRequestDto songRequestDto,
                                                            @RequestParam Integer id) {
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        databaseInMemory.put(id, new SongEntity(songRequestDto.songName(), songRequestDto.artistName()));
        log.info("Song updated: {}", songRequestDto);
        return ResponseEntity.ok(new SingleSongResponseDto(songRequestDto.songName(), songRequestDto.artistName()));
    }

    @PatchMapping("/songs/")
    public ResponseEntity<SingleSongResponseDto> updateSongByPatch(@RequestBody @Valid PartiallySongRequestDto songRequestDto,
                                                                   @RequestParam Integer id) {
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }

        String nameToUpdate = songEntity.name();
        String artistNameToUpdate = songEntity.artistName();
        if (songRequestDto.songName() != null) {
            nameToUpdate = songRequestDto.songName();
            log.info("New name is: {}", nameToUpdate);
        }
        if (songRequestDto.artistName() != null) {
            artistNameToUpdate = songRequestDto.artistName();
            log.info("New artist name is: {}", artistNameToUpdate);
        }

        SongEntity updatedSong = new SongEntity(nameToUpdate, artistNameToUpdate);
        databaseInMemory.put(id, updatedSong);
        log.info("Song updated: {}", updatedSong);
        return ResponseEntity.ok(new SingleSongResponseDto(updatedSong.name(), updatedSong.artistName()));
    }
}