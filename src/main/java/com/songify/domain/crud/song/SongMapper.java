package com.songify.domain.crud.song;

import com.songify.infrastructure.controller.dto.response.*;
import com.songify.song.domain.model.Song;
import com.songify.infrastructure.controller.dto.request.PutSongRequestDto;
import com.songify.infrastructure.controller.dto.request.SongPostRequestDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {
    public static Song mapSongPostRequestDtoToSongEntity(SongPostRequestDto dto) {
        return new Song(dto.name(), dto.artistName());
    }

    public static SongPostResponseDto mapSongEntityToSongPostResponseDto(Song song) {
        SongDto songDto = mapFromSongToSongDto(song);
        return new SongPostResponseDto(songDto);
    }

    @NotNull
    private static SongDto mapFromSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
    }

    public static SingleSongResponseDtoById mapSongEntityToSingleSongResponseDtoById(Song song) {
        return new SingleSongResponseDtoById(mapFromSongToSongDto(song));
    }

    public static Song mapPutSongRequestDtoToSongEntity(PutSongRequestDto putSongRequestDto) {
        return new Song(putSongRequestDto.songName(), putSongRequestDto.artistName());
    }

    public static PutSongResponseDto mapSongEntityToPutSongResponseDto(Song song) {
        return new PutSongResponseDto(mapFromSongToSongDto(song));
    }

    public static PartiallyUpdateSongResponseDto mapSongEntityToPartiallyUpdateSongResponse(Song song) {
        return new PartiallyUpdateSongResponseDto(mapFromSongToSongDto(song));
    }

    public static DeleteSongResponseDto mapSongEntityToDeleteSongResponseDto(Song removed) {
        return new DeleteSongResponseDto("Deleted song " + removed, HttpStatus.OK);
    }

    public static GetAllSongsResponseDto mapSongEntitiesToGetAllSongsResponseDto(List<Song> songs) {
        List<SongDto> songDtos = songs.stream()
                                      .map(song -> new SongDto(song.getId(), song.getName(), song.getArtist()))
                                      .toList();
        return new GetAllSongsResponseDto(songDtos);
    }
}