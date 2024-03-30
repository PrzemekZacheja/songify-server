package com.songify.domain.crud.dto;

import java.time.Instant;
import java.util.Set;

public record AlbumRequestDto(String title,
                              Instant releaseDate,
                              Set<Long> songsIds) {
}