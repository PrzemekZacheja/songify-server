package com.songify.domain.crud.dto;

import com.songify.domain.crud.SongLanguage;
import lombok.Builder;

@Builder
public record SongDto(
        Long id,
        String name,
        Long duration,
        SongLanguage language) {
}