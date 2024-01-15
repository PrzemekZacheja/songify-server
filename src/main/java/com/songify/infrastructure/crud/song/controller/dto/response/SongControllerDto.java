package com.songify.infrastructure.crud.song.controller.dto.response;

import com.songify.domain.crud.song.SongLanguage;
import lombok.Builder;

@Builder
public record SongControllerDto(Long id,
                                String name,
                                String artist,
                                Long duration,
                                SongLanguage language) {
}