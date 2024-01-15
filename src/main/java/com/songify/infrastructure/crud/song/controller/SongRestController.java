package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.song.SongCrudFacade;
import com.songify.domain.crud.song.SongDomainDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PutSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.SongPostRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PutSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SingleSongResponseDtoById;
import com.songify.infrastructure.crud.song.controller.dto.response.SongPostResponseDto;
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

    private final SongCrudFacade songCrudFacade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@PageableDefault() Pageable pageable) {
        List<SongDomainDto> songs = songCrudFacade.findAll(pageable);
        GetAllSongsResponseDto getAllSongsResponseDto = mapSongDomainDtoListToGetAllSongsResponseDto(songs);
        return ResponseEntity.ok(getAllSongsResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleSongResponseDtoById> getSongById(@PathVariable("id") long id,
                                                                 @RequestHeader(required = false) String authorizationHeader) {
        SongDomainDto song = songCrudFacade.getById(id);
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
    public ResponseEntity<SongPostResponseDto> createSong(@RequestBody @Valid SongPostRequestDto songPostRequestDto) {
        SongDomainDto songDomainDto = SongControllerMapper.mapSongPostRequestDtoToSongDomainDto(songPostRequestDto);
        SongDomainDto addedSong = songCrudFacade.addSong(songDomainDto);
        SongPostResponseDto songPostResponseDto = SongControllerMapper.mapSongDomainDtoToSongPostResponseDto(addedSong);
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
        SongDomainDto songToDelete = songCrudFacade.getById(id);
        songCrudFacade.deleteById(id);
        DeleteSongResponseDto deletedSongResponseDto = SongControllerMapper.mapSongDomainDtoToDeleteSongResponseDto(
                songToDelete);
        return ResponseEntity.ok(deletedSongResponseDto);
    }

    @PutMapping
    public ResponseEntity<PutSongResponseDto> updateSong(@RequestBody @Valid PutSongRequestDto putSongRequestDto,
                                                         @RequestParam Long id) {
        SongDomainDto songToPut = SongControllerMapper.mapPutSongRequestDtoToSongDomainDto(putSongRequestDto);
        songCrudFacade.updateById(id, songToPut);
        return ResponseEntity.ok(SongControllerMapper.mapSongDomainDtoToPutSongResponseDto(songToPut));
    }

    @PatchMapping
    public ResponseEntity<PartiallyUpdateSongResponseDto> updateSongByPatch(
            @RequestBody PartiallyUpdateSongRequestDto partiallySongRequestDto,
            @RequestParam Long id) {
        SongDomainDto updatedSong = songCrudFacade.partiallyUpdateById(id, partiallySongRequestDto);
        return ResponseEntity.ok(SongControllerMapper.mapSongDomainDtoToPartiallyUpdateSongResponseDto(updatedSong));
    }
}