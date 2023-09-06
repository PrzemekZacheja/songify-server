package com.songify.song.infrastructure.controller;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.service.*;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongPostRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/songs")
public class SongRestController {

    private final SongAdder songAdder;
    private final SongProvider songProvider;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limitOfSongs) {
        if (limitOfSongs == null) {
            return ResponseEntity.ok(SongMapper.mapSongEntitiesToGetAllSongsResponseDto(songProvider.findAll()));
        }
        List<Song> limitedSongs = songProvider.getLimitedSongs(limitOfSongs);
        return ResponseEntity.ok(SongMapper.mapSongEntitiesToGetAllSongsResponseDto(limitedSongs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDtoById> getSongById(@PathVariable("id") long id,
                                                                 @RequestHeader(required = false) String authorizationHeader) {
        Song song = songProvider.getById(id);
        if (authorizationHeader != null) {
            log.info("Authorization header: {}", authorizationHeader);
        }
        SingleSongResponseDtoById singleSongResponseDtoById = SongMapper.mapSongEntityToSingleSongResponseDtoById(song);
        log.info("Song found: {}", singleSongResponseDtoById);
        return ResponseEntity.ok(singleSongResponseDtoById);
    }

    @PostMapping
    public ResponseEntity<SongPostResponseDto> createSong(@RequestBody @Valid SongPostRequestDto songPostRequestDto) {
        Song song = SongMapper.mapSongPostRequestDtoToSongEntity(songPostRequestDto);
        Song addedSong = songAdder.addSong(song);
        SongPostResponseDto songPostResponseDto = SongMapper.mapSongEntityToSongPostResponseDto(addedSong);
        return ResponseEntity.ok(songPostResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByPathVariable(@PathVariable("id") Long id) {
        return getDeleteSongResponseDtoResponseEntity(id);
    }

    @DeleteMapping
    public ResponseEntity<DeleteSongResponseDto> deleteSongByQueryParam(@RequestParam("id") Long id) {
        return getDeleteSongResponseDtoResponseEntity(id);
    }

    private ResponseEntity<DeleteSongResponseDto> getDeleteSongResponseDtoResponseEntity(@RequestParam("id") Long id) {
        Song songToDelete = songProvider.getById(id);
        DeleteSongResponseDto deletedSongResponseDto = SongMapper.mapSongEntityToDeleteSongResponseDto(songToDelete);
        songDeleter.deleteById(id);
        return ResponseEntity.ok(deletedSongResponseDto);
    }

    @PutMapping
    public ResponseEntity<PutSongResponseDto> updateSong(@RequestBody @Valid PutSongRequestDto putSongRequestDto,
                                                         @RequestParam Long id) {
        Song songToPut = SongMapper.mapPutSongRequestDtoToSongEntity(putSongRequestDto);
        songUpdater.updateById(id, songToPut);
        PutSongResponseDto putSongResponseDto = SongMapper
                .mapSongEntityToPutSongResponseDto(songToPut);
        return ResponseEntity.ok(putSongResponseDto);
    }

    @PatchMapping
    public ResponseEntity<PartiallyUpdateSongResponseDto> updateSongByPatch(
            @RequestBody PartiallyUpdateSongRequestDto partiallySongRequestDto,
            @RequestParam Long id) {
        Song updatedSong = songUpdater.partiallyUpdateById(id, partiallySongRequestDto);
        PartiallyUpdateSongResponseDto body = SongMapper.mapSongEntityToPartiallyUpdateSongResponse(updatedSong);
        return ResponseEntity.ok(body);
    }
}