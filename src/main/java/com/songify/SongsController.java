package com.songify;

import lombok.extern.log4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Log4j2
public class SongsController {

    Map<Integer, SongDto> databaseInMemory = new HashMap<>();

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(@RequestParam(required = false) Integer id) {
        databaseInMemory.put(1, new SongDto("Song1"));
        databaseInMemory.put(2, new SongDto("Song2"));
        databaseInMemory.put(3, new SongDto("Song3"));
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
    public ResponseEntity<SongDto> getSongById(@PathVariable("id") int id,
                                               @RequestHeader String  authorizationHeader) {
        log.info("Authorization header: {}", authorizationHeader);
        SongDto songDto = databaseInMemory.get(id);
        if (songDto == null) {
            return ResponseEntity.notFound()
                                 .build();
        }
        return ResponseEntity.ok(songDto);
    }

}