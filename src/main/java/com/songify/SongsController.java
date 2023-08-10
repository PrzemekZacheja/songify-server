package com.songify;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SongsController {

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs() {
        SongResponseDto songResponseDto = new SongResponseDto(Arrays.asList("Song 1", "Song 2", "Song 3"));
        return ResponseEntity.ok(songResponseDto);
    }

}