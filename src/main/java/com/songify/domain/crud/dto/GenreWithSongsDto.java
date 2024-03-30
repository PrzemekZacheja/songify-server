package com.songify.domain.crud.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GenreWithSongsDto(Long id,
                                String name,
                                List<SongDto> songs) {
}