package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongPostRequestDto;
import com.songify.song.infrastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.PutSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.SingleSongResponseDtoById;
import com.songify.song.infrastructure.controller.dto.response.SongPostResponseDto;

public class Mapper {
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
}