package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongPostRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class SongMapper {
    public static SongEntity mapSongPostRequestDtoToSongEntity(SongPostRequestDto dto) {
        return new SongEntity(dto.name(), dto.artistName());
    }

    public static SongPostResponseDto mapSongEntityToSongPostResponseDto(SongEntity songEntity) {
        return new SongPostResponseDto(songEntity.name(), songEntity.artistName());
    }

    public static SingleSongResponseDtoById mapSongEntityToSingleSongResponseDtoById(SongEntity songEntity) {
        return new SingleSongResponseDtoById(songEntity.name(), songEntity.artistName());
    }

    public static SongEntity mapPutSongRequestDtoToSongEntity(PutSongRequestDto putSongRequestDto) {
        return new SongEntity(putSongRequestDto.name(), putSongRequestDto.artistName());
    }

    public static PutSongResponseDto mapSongEntityToPutSongResponseDto(SongEntity songEntity) {
        return new PutSongResponseDto(songEntity.name(), songEntity.artistName());
    }

    public static PartiallyUpdateSongResponseDto mapSongEntityToPartiallyUpdateSongResponse(SongEntity songEntity) {
        return new PartiallyUpdateSongResponseDto(songEntity.name(), songEntity.artistName());
    }

    public static DeleteSongResponseDto mapSongEntityToDeleteSongResponseDto(SongEntity removed) {
        return new DeleteSongResponseDto("Deleted song " + removed, HttpStatus.OK);
    }

    public static GetAllSongsResponseDto mapSongEntitiesToGetAllSongsResponseDto(Map<Integer, SongEntity> allSongs) {
        return new GetAllSongsResponseDto(allSongs);
    }
}