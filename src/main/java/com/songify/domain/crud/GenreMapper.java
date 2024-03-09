package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;

class GenreMapper {
    static GenreDto mapFromGenreToGenreDto(Genre genre) {
        return GenreDto.builder()
                       .id(genre.getId())
                       .name(genre.getName())
                       .build();
    }
}