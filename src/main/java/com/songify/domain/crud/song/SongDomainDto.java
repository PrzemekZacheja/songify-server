package com.songify.domain.crud.song;

import lombok.Builder;

@Builder
public record SongDomainDto(
        Long id,
        String name,
        String artist,
        Long duration,
        SongLanguage language) {
}