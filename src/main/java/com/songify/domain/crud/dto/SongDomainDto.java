package com.songify.domain.crud.dto;

import com.songify.domain.crud.SongLanguage;
import lombok.Builder;

@Builder
public record SongDomainDto(
        Long id,
        String name,
        String artist,
        Long duration,
        SongLanguage language) {
}