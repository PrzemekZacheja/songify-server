package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SingleSongResponseDtoById;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.songify.infrastructure.crud.song.controller.SongControllerMapper.mapSongDomainDtoListToGetAllSongsResponseDto;


@RestController
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@RequestMapping("/songs")
public class SongRestController {

    private final SongifyCrudFacade songifyCrudFacade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault() Pageable pageable) {
        List<SongDto> songs = songifyCrudFacade.findAllSongs(pageable);
        GetAllSongsResponseDto getAllSongsResponseDto = mapSongDomainDtoListToGetAllSongsResponseDto(songs);
        return ResponseEntity.ok(getAllSongsResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDtoById> getSongById(@PathVariable("id") long id,
                                                                 @RequestHeader(required = false) String authorizationHeader) {
        SongDto song = songifyCrudFacade.findSongById(id);
        if (authorizationHeader != null) {
            log.info("Authorization header: {}", authorizationHeader);
        }
        SingleSongResponseDtoById singleSongResponseDtoById =
                SongControllerMapper.mapSongDomainDtoToSingleSongResponseDtoById(
                        song);
        log.info("Song found: {}", singleSongResponseDtoById);
        return ResponseEntity.ok(singleSongResponseDtoById);
    }

    @PostMapping
    public ResponseEntity<SongDto> createSong(@RequestBody @Valid SongRequestDto songRequestDto) {
        SongDto addedSong = songifyCrudFacade.addSong(songRequestDto);
        return ResponseEntity.ok(addedSong);
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
        SongDto songToDelete = songifyCrudFacade.findSongById(id);
        songifyCrudFacade.deleteSongById(id);
        DeleteSongResponseDto deletedSongResponseDto = SongControllerMapper.mapSongDomainDtoToDeleteSongResponseDto(
                songToDelete);
        return ResponseEntity.ok(deletedSongResponseDto);
    }

    @PutMapping
    public ResponseEntity<SongDto> updateSong(@RequestBody @Valid SongRequestDto songRequestDto,
                                                         @RequestParam Long id) {
        SongDto songDto = songifyCrudFacade.updateSongById(id, songRequestDto);
        return ResponseEntity.ok(songDto);
    }

    @PatchMapping
    public ResponseEntity<PartiallyUpdateSongResponseDto> updateSongByPatch(
            @RequestBody PartiallyUpdateSongRequestDto partiallySongRequestDto,
            @RequestParam Long id) {
        SongDto updatedSong = songifyCrudFacade.partiallyUpdateSongById(id, partiallySongRequestDto);
        return ResponseEntity.ok(SongControllerMapper.mapSongDomainDtoToPartiallyUpdateSongResponseDto(updatedSong));
    }
}