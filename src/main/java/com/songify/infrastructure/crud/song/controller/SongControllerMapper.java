package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SingleSongResponseDtoById;
import com.songify.infrastructure.crud.song.controller.dto.response.SongControllerDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongControllerMapper {


    public static SingleSongResponseDtoById mapSongDomainDtoToSingleSongResponseDtoById(SongDto song) {
        return new SingleSongResponseDtoById(SongControllerDto.builder()
                                                              .id(song.id())
                                                              .name(song.name())
                                                              .duration(song.duration())
                                                              .language(song.language())
                                                              .build());
    }

    public static DeleteSongResponseDto mapSongDomainDtoToDeleteSongResponseDto(SongDto removed) {
        return new DeleteSongResponseDto("Deleted song " + removed, HttpStatus.OK);
    }

    public static GetAllSongsResponseDto mapSongDomainDtoListToGetAllSongsResponseDto(List<SongDto> songs) {
        List<SongControllerDto> songControllerDtos = songs.stream()
                                                          .map(SongControllerMapper::mapSongDomainDtoToSongControllerDto)
                                                          .toList();
        return new GetAllSongsResponseDto(songControllerDtos);
    }

    private static SongControllerDto mapSongDomainDtoToSongControllerDto(final SongDto song) {
        return SongControllerDto.builder()
                                .id(song.id())
                                .name(song.name())
                                .duration(song.duration())
                                .language(song.language())
                                .build();
    }

    static PartiallyUpdateSongResponseDto mapSongDomainDtoToPartiallyUpdateSongResponseDto(final SongDto updatedSong) {
        return new PartiallyUpdateSongResponseDto(SongControllerDto.builder()
                                                                   .id(updatedSong.id())
                                                                   .name(updatedSong.name())
                                                                   .duration(updatedSong.duration())
                                                                   .language(updatedSong.language())
                                                                   .build());
    }

}