package com.songify.domain.crud.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record ArtistWithAlbumsDto(Long id,
                                  String name,
                                  Set<AlbumDto> albums) {
}