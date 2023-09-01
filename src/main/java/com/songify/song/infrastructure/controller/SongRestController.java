package com.songify.song.infrastructure.controller;

import com.songify.song.domain.model.Song;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongMapper;
import com.songify.song.domain.service.SongProvider;
import com.songify.song.infrastructure.controller.dto.request.SongPostRequestDto;
import com.songify.song.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import com.songify.song.infrastructure.controller.dto.response.SingleSongResponseDtoById;
import com.songify.song.infrastructure.controller.dto.response.SongPostResponseDto;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Song> limitedSongs = songProvider.getLimitedSongs(limitOfSongs);
        return ResponseEntity.ok(SongMapper.mapSongEntitiesToGetAllSongsResponseDto(limitedSongs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDtoById> getSongById(@PathVariable("id") long id, @RequestHeader(required = false) String authorizationHeader) {
        Song song = songProvider.getById(id)
                                .orElseThrow(
                                        () -> new SongNotFoundException(SONG_NOT_FOUND
                                        ));
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

//    @DeleteMapping("/{id}")
//    public ResponseEntity<DeleteSongResponseDto> deleteSongByPathVariable(@PathVariable("id") Integer id) {
//        return getDeleteSongResponseDtoResponseEntity(id);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<DeleteSongResponseDto> deleteSongByQueryParam(@RequestParam("id") Integer id) {
//        return getDeleteSongResponseDtoResponseEntity(id);
//    }

//    private ResponseEntity<DeleteSongResponseDto> getDeleteSongResponseDtoResponseEntity(@RequestParam("id") Integer id) {
//        Song song = songProvider.getById(id);
//        if (song == null) {
//            throw new SongNotFoundException(SONG_NOT_FOUND);
//        }
//        Song removed = songProvider.remove(id);
//        log.info("Song deleted: {}", removed);
//        DeleteSongResponseDto deleteSongResponseDto = SongMapper.mapSongEntityToDeleteSongResponseDto(removed);
//        return ResponseEntity.ok(deleteSongResponseDto);
//    }

//    @PutMapping
//    public ResponseEntity<PutSongResponseDto> updateSong(@RequestBody @Valid PutSongRequestDto putSongRequestDto, @RequestParam Integer id) {
//        Song song = songProvider.getById(id);
//        if (song == null) {
//            throw new SongNotFoundException(SONG_NOT_FOUND);
//        }
//        Song songToPut = SongMapper.mapPutSongRequestDtoToSongEntity(putSongRequestDto);
//        songProvider.put(id, songToPut);
//        log.info("Song updated: {}", songToPut);
//        return ResponseEntity.ok(SongMapper.mapSongEntityToPutSongResponseDto(songToPut));
//    }

//    @PatchMapping
//    public ResponseEntity<PartiallyUpdateSongResponseDto> updateSongByPatch(
//            @RequestBody PartiallyUpdateSongRequestDto partiallySongRequestDto,
//            @RequestParam Integer id) {
//        Song song = songProvider.getById(id);
//        if (song == null) {
//            throw new SongNotFoundException(SONG_NOT_FOUND);
//        }
//
//        String nameToUpdate = song.getName();
//        String artistNameToUpdate = song.getArtist();
//        if (!partiallySongRequestDto.name().isEmpty()) {
//            nameToUpdate = partiallySongRequestDto.name();
//            log.info("New name is: {}", nameToUpdate);
//        }
//        if (!partiallySongRequestDto.artistName().isEmpty()) {
//            artistNameToUpdate = partiallySongRequestDto.artistName();
//            log.info("New artist name is: {}", artistNameToUpdate);
//        }
//
//        Song updatedSong = new Song(nameToUpdate, artistNameToUpdate);
//        songProvider.put(id, updatedSong);
//        PartiallyUpdateSongResponseDto body = SongMapper.mapSongEntityToPartiallyUpdateSongResponse(updatedSong);
//        return ResponseEntity.ok(body);
//    }
}