package com.songify.song.domain.service;

import com.songify.song.domain.model.Song;
import com.songify.song.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.SongPostRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {
    public static Song mapSongPostRequestDtoToSongEntity(SongPostRequestDto dto) {
        return new Song(dto.name(), dto.artistName());
    }

    public static SongPostResponseDto mapSongEntityToSongPostResponseDto(Song song) {
        return new SongPostResponseDto(song.getName(), song.getArtist());
    }

    public static SingleSongResponseDtoById mapSongEntityToSingleSongResponseDtoById(Song song) {
        return new SingleSongResponseDtoById(song.getName(), song.getArtist());
    }

    public static Song mapPutSongRequestDtoToSongEntity(@Valid PutSongRequestDto putSongRequestDto) {
        return new Song(putSongRequestDto.name(), putSongRequestDto.artistName());
    }

    public static PutSongResponseDto mapSongEntityToPutSongResponseDto(Song song) {
        return new PutSongResponseDto(song.getName(), song.getArtist());
    }

    public static PartiallyUpdateSongResponseDto mapSongEntityToPartiallyUpdateSongResponse(Song song) {
        return new PartiallyUpdateSongResponseDto(song.getName(), song.getArtist());
    }

    public static DeleteSongResponseDto mapSongEntityToDeleteSongResponseDto(Song removed) {
        return new DeleteSongResponseDto("Deleted song " + removed, HttpStatus.OK);
    }

    public static GetAllSongsResponseDto mapSongEntitiesToGetAllSongsResponseDto(List<Song> allSongs) {
        return new GetAllSongsResponseDto(allSongs);
    }
}