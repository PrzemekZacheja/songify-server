package com.songify.infrastructure.crud.song.controller.dto.request;

import com.songify.domain.crud.SongLanguage;

public record PartiallyUpdateSongRequestDto(String name,
                                            String artist,
                                            Long duration,
                                            SongLanguage language) {
}