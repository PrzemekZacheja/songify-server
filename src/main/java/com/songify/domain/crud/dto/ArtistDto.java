package com.songify.domain.crud.dto;

import lombok.Builder;

@Builder
public record ArtistDto(Long id,
                        String name) {
}