package com.songify.song.infrastructure.controller;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongMapper;
import com.songify.song.domain.service.SongProvider;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongPostRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    public static final String SONG_NOT_FOUND = "Song not found";
    private final SongAdder songAdder;
    private final SongProvider songProvider;

    public SongRestController(SongAdder songAdder, SongProvider songProvider) {
        this.songAdder = songAdder;
        this.songProvider = songProvider;
    }

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limitOfSongs) {
        if (limitOfSongs == null) {
            return ResponseEntity.ok(SongMapper.mapSongEntitiesToGetAllSongsResponseDto(songProvider.findAll()));
        }
        Map<Integer, SongEntity> limitedSongs = songProvider.getLimitedSongs(limitOfSongs);
        return ResponseEntity.ok(SongMapper.mapSongEntitiesToGetAllSongsResponseDto(limitedSongs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDtoById> getSongById(@PathVariable("id") int id, @RequestHeader(required = false) String authorizationHeader) {
        if (authorizationHeader != null) {
            log.info("Authorization header: {}", authorizationHeader);
        }
        SongEntity songEntity = songProvider.getById(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        SingleSongResponseDtoById singleSongResponseDtoById = SongMapper.mapSongEntityToSingleSongResponseDtoById(songEntity);
        log.info("Song found: {}", singleSongResponseDtoById);
        return ResponseEntity.ok(singleSongResponseDtoById);
    }

    @PostMapping
    public ResponseEntity<SongPostResponseDto> createSong(@RequestBody @Valid SongPostRequestDto songPostRequestDto) {
        SongEntity songEntity = SongMapper.mapSongPostRequestDtoToSongEntity(songPostRequestDto);
        SongEntity addedSong = songAdder.addSong(songEntity);
        SongPostResponseDto songPostResponseDto = SongMapper.mapSongEntityToSongPostResponseDto(addedSong);
        return ResponseEntity.ok(songPostResponseDto);
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
        SongEntity songEntity = songProvider.getById(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        SongEntity removed = songProvider.remove(id);
        log.info("Song deleted: {}", removed);
        DeleteSongResponseDto deleteSongResponseDto = SongMapper.mapSongEntityToDeleteSongResponseDto(removed);
        return ResponseEntity.ok(deleteSongResponseDto);
    }

    @PutMapping
    public ResponseEntity<PutSongResponseDto> updateSong(@RequestBody @Valid PutSongRequestDto putSongRequestDto, @RequestParam Integer id) {
        SongEntity songEntity = songProvider.getById(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }
        SongEntity songToPut = SongMapper.mapPutSongRequestDtoToSongEntity(putSongRequestDto);
        songProvider.put(id, songToPut);
        log.info("Song updated: {}", songToPut);
        return ResponseEntity.ok(SongMapper.mapSongEntityToPutSongResponseDto(songToPut));
    }

    @PatchMapping
    public ResponseEntity<PartiallyUpdateSongResponseDto> updateSongByPatch(
            @RequestBody PartiallyUpdateSongRequestDto partiallySongRequestDto,
            @RequestParam Integer id) {
        SongEntity songEntity = songProvider.getById(id);
        if (songEntity == null) {
            throw new SongNotFoundException(SONG_NOT_FOUND);
        }

        String nameToUpdate = songEntity.name();
        String artistNameToUpdate = songEntity.artistName();
        if (!partiallySongRequestDto.name().isEmpty()) {
            nameToUpdate = partiallySongRequestDto.name();
            log.info("New name is: {}", nameToUpdate);
        }
        if (!partiallySongRequestDto.artistName().isEmpty()) {
            artistNameToUpdate = partiallySongRequestDto.artistName();
            log.info("New artist name is: {}", artistNameToUpdate);
        }

        SongEntity updatedSong = new SongEntity(nameToUpdate, artistNameToUpdate);
        songProvider.put(id, updatedSong);
        PartiallyUpdateSongResponseDto body = SongMapper.mapSongEntityToPartiallyUpdateSongResponse(updatedSong);
        return ResponseEntity.ok(body);
    }
}