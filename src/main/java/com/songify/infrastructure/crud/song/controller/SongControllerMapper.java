package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.dto.SongDomainDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.PutSongRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.request.SongPostRequestDto;
import com.songify.infrastructure.crud.song.controller.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.PutSongResponseDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SingleSongResponseDtoById;
import com.songify.infrastructure.crud.song.controller.dto.response.SongControllerDto;
import com.songify.infrastructure.crud.song.controller.dto.response.SongPostResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongControllerMapper {

    public static SongPostResponseDto mapSongDomainDtoToSongPostResponseDto(SongDomainDto song) {
        SongControllerDto songControllerDto = SongControllerDto.builder()
                                                               .artist(song.artist())
                                                               .id(song.id())
                                                               .name(song.name())
                                                               .language(song.language())
                                                               .duration(song.duration())
                                                               .build();
        return new SongPostResponseDto(songControllerDto);
    }


    public static SingleSongResponseDtoById mapSongDomainDtoToSingleSongResponseDtoById(SongDomainDto song) {
        return new SingleSongResponseDtoById(SongControllerDto.builder()
                                                              .artist(song.artist())
                                                              .id(song.id())
                                                              .name(song.name())
                                                              .duration(song.duration())
                                                              .language(song.language())
                                                              .build());
    }

    public static DeleteSongResponseDto mapSongDomainDtoToDeleteSongResponseDto(SongDomainDto removed) {
        return new DeleteSongResponseDto("Deleted song " + removed, HttpStatus.OK);
    }

    public static GetAllSongsResponseDto mapSongDomainDtoListToGetAllSongsResponseDto(List<SongDomainDto> songs) {
        List<SongControllerDto> songControllerDtos = songs.stream()
                                                          .map(SongControllerMapper::mapSongDomainDtoToSongControllerDto)
                                                          .toList();
        return new GetAllSongsResponseDto(songControllerDtos);
    }

    private static SongControllerDto mapSongDomainDtoToSongControllerDto(final SongDomainDto song) {
        return SongControllerDto.builder()
                                .artist(song.artist())
                                .id(song.id())
                                .name(song.name())
                                .duration(song.duration())
                                .language(song.language())
                                .build();
    }

    static SongDomainDto mapSongPostRequestDtoToSongDomainDto(final SongPostRequestDto songPostRequestDto) {
        return SongDomainDto.builder()
                            .artist(songPostRequestDto.artist())
                            .name(songPostRequestDto.name())
                            .duration(songPostRequestDto.duration())
                            .language(songPostRequestDto.language())
                            .build();
    }

    static SongDomainDto mapPutSongRequestDtoToSongDomainDto(final PutSongRequestDto putSongRequestDto) {
        return SongDomainDto.builder()
                            .artist(putSongRequestDto.artistName())
                            .name(putSongRequestDto.songName())
                            .duration(putSongRequestDto.duration() != null ? putSongRequestDto.duration() : 0L)
                            .language(putSongRequestDto.language())
                            .build();
    }

    static PutSongResponseDto mapSongDomainDtoToPutSongResponseDto(final SongDomainDto songToPut) {
        return new PutSongResponseDto(SongControllerDto.builder()
                                                       .artist(songToPut.artist())
                                                       .id(songToPut.id())
                                                       .name(songToPut.name())
                                                       .build());
    }

    static PartiallyUpdateSongResponseDto mapSongDomainDtoToPartiallyUpdateSongResponseDto(final SongDomainDto updatedSong) {
        return new PartiallyUpdateSongResponseDto(SongControllerDto.builder()
                                                                   .artist(updatedSong.artist())
                                                                   .id(updatedSong.id())
                                                                   .name(updatedSong.name())
                                                                   .duration(updatedSong.duration())
                                                                   .language(updatedSong.language())
                                                                   .build());
    }

    public static SongDomainDto mapFromPartiallyUpdateSongRequestDtoToSong(final PartiallyUpdateSongRequestDto partiallySongRequestDto,
                                                                           final Long id) {
        return SongDomainDto.builder()
                            .id(id)
                            .artist(partiallySongRequestDto.artist())
                            .name(partiallySongRequestDto.name())
                            .language(partiallySongRequestDto.language())
                            .duration(partiallySongRequestDto.duration() != null ?
                                              partiallySongRequestDto.duration() : 0L)
                            .build();
    }
}