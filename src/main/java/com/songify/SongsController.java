package com.songify;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class SongsController {

    Map<Integer, SongDto> databaseInMemory = new HashMap<>(
            Map.of(
                    1, new SongDto("Song1"),
                    2, new SongDto("Song2"),
                    3, new SongDto("Song3"),
                    4, new SongDto("Song4")));

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer id) {

        if (id != null) {
            SongDto songDto = databaseInMemory.get(id);
            if (songDto == null) {
                return ResponseEntity.notFound()
                                     .build();
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
            return ResponseEntity.notFound()
                                 .build();
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
}