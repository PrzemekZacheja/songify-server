package com.songify.infrastructure.crud.song.controller.dto.request;


import com.songify.domain.crud.SongLanguage;

public record SongPostRequestDto(String name,
                                 String artist,
                                 Long duration,
                                 SongLanguage language) {
}