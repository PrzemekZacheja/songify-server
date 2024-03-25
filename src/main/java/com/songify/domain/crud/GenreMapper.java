package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreWithSongsDto;
import com.songify.domain.crud.dto.SongDto;

import java.util.List;

class GenreMapper {
	static GenreDto mapFromGenreToGenreDto(Genre genre) {
		return GenreDto.builder()
		               .id(genre.getId())
		               .name(genre.getName())
		               .build();
	}

	static GenreWithSongsDto mapFromGenreToGenreWithSongsDto(final Genre genre, final List<SongDto> songsByGenreId) {
		return GenreWithSongsDto.builder()
		                        .songs(songsByGenreId)
		                        .id(genre.getId())
		                        .name(genre.getName())
		                        .build();
	}
}