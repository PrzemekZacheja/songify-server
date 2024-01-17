package com.songify.domain.crud;

import lombok.Builder;

@Builder
public record SongDomainDto(
        Long id,
        String name,
        String artist,
        Long duration,
        SongLanguage language) {
}