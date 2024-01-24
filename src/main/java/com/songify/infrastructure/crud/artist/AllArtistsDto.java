package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.Builder;

import java.util.Set;

@Builder
record AllArtistsDto(Set<ArtistDto> artists) {
}