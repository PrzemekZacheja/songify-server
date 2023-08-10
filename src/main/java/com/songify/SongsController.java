package com.songify;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SongsController {

    Map<Integer, SongDto> databaseInMemory = new HashMap<>();

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs() {
        databaseInMemory.put(1, new SongDto("Song1"));
        databaseInMemory.put(2, new SongDto("Song2"));
        databaseInMemory.put(3, new SongDto("Song3"));
        SongResponseDto songResponseDto = new SongResponseDto(databaseInMemory);
        return ResponseEntity.ok(songResponseDto);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable("id") int id) {
        SongDto songDto = databaseInMemory.get(id);
        if (songDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(songDto);
    }

    @GetMapping("/songs/")
    public ResponseEntity<SongDto> getSongByIdRequestParam(@RequestParam("id") Integer id) {
        SongDto songDto = databaseInMemory.get(id);
        if (songDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(songDto);
    }
}