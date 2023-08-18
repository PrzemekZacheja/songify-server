package com.songify.song.controller;

import com.songify.song.dto.SongEntity;
import com.songify.song.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.dto.request.PutSongRequestDto;
import com.songify.song.dto.request.SongPostRequestDto;
import com.songify.song.dto.response.*;
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
@RequestMapping("/songs")
public class SongRestController {

    public static final String SONG_NOT_FOUND = "Song not found";
    Map<Integer, SongEntity> databaseInMemory = new HashMap<>(
            Map.of(1, new SongEntity("The Beatles", "Let it Be"),
                   2, new SongEntity("The Beatles", "Hey Jude"),
                   3, new SongEntity("The Beatles", "Sgt. Pepper's Lonely Hearts Club Band"),
                   4, new SongEntity("The Beatles", "A Hard Day's Night")));

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer id) {
        if (id != null) {
            SongEntity songEntity = databaseInMemory.get(id);
            if (songEntity == null) {
                throw new SongNotFoundException(SONG_NOT_FOUND);
            }
            GetAllSongsResponseDto singleMapOfSongDto = new GetAllSongsResponseDto(Map.of(id, songEntity));
            log.info("Song found: {}", singleMapOfSongDto);
            return ResponseEntity.ok(singleMapOfSongDto);
        }
        log.info("All songs: {}", databaseInMemory);
        return ResponseEntity.ok(new GetAllSongsResponseDto(databaseInMemory));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDtoById> getSongById(@PathVariable("id") int id, @RequestHeader(required = false) String authorizationHeader) {
        if (authorizationHeader != null) {
            log.info("Authorization header: {}", authorizationHeader);
        }
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        log.info("Song found: {}", songEntity);
        return ResponseEntity.ok(new SingleSongResponseDtoById(songEntity.name(), songEntity.artistName()));
    }

    @PostMapping
    public ResponseEntity<SongPostResponseDto> createSong(@RequestBody @Valid SongPostRequestDto songPostRequestDto) {
        int key = databaseInMemory.size() + 1;
        databaseInMemory.put(key, new SongEntity(songPostRequestDto.name(), songPostRequestDto.artistName()));
        log.info("Song created: {}", songPostRequestDto);
        return ResponseEntity.ok(new SongPostResponseDto(songPostRequestDto.name(), songPostRequestDto.artistName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByPathVariable(@PathVariable("id") Integer id) {
        return getDeleteSongResponseDtoResponseEntity(id);
    }

    @DeleteMapping
    public ResponseEntity<DeleteSongResponseDto> deleteSongByQueryParam(@RequestParam("id") Integer id) {
        return getDeleteSongResponseDtoResponseEntity(id);
    }

    private ResponseEntity<DeleteSongResponseDto> getDeleteSongResponseDtoResponseEntity(@RequestParam("id") Integer id) {
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        databaseInMemory.remove(id);
        log.info("Song deleted: {}", id);
        return ResponseEntity.ok(new DeleteSongResponseDto("Deleted song " + id, HttpStatus.OK));
    }

    @PutMapping
    public ResponseEntity<PutSongResponseDto> updateSong(@RequestBody @Valid PutSongRequestDto putSongRequestDto,
                                                         @RequestParam Integer id) {
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        databaseInMemory.put(id, new SongEntity(putSongRequestDto.name(), putSongRequestDto.artistName()));
        log.info("Song updated: {}", putSongRequestDto);
        return ResponseEntity.ok(new PutSongResponseDto(putSongRequestDto.name(), putSongRequestDto.artistName()));
    }

    @PatchMapping
    public ResponseEntity<PartiallyUpdateSongResponseDto> updateSongByPatch(@RequestBody PartiallyUpdateSongRequestDto partiallySongRequestDto,
                                                                            @RequestParam Integer id) {
        SongEntity songEntity = databaseInMemory.get(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }

        String nameToUpdate = songEntity.name();
        String artistNameToUpdate = songEntity.artistName();
        if (partiallySongRequestDto.songName() != null) {
            nameToUpdate = partiallySongRequestDto.songName();
            log.info("New name is: {}", nameToUpdate);
        }
        if (partiallySongRequestDto.artistName() != null) {
            artistNameToUpdate = partiallySongRequestDto.artistName();
            log.info("New artist name is: {}", artistNameToUpdate);
        }

        SongEntity updatedSong = new SongEntity(nameToUpdate, artistNameToUpdate);
        databaseInMemory.put(id, updatedSong);
        log.info("Song updated: {}", updatedSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong.name(), updatedSong.artistName()));
    }
}