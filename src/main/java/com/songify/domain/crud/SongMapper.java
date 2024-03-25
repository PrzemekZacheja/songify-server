package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.jetbrains.annotations.NotNull;

class SongMapper {

	@NotNull
	public static SongDto mapFromSongToSongDto(Song song) {
		return SongDto.builder()
		              .duration(song.getDuration())
		              .language(song.getLanguage())
		              .name(song.getName())
		              .id(song.getId())
		              .build();
	}

	static Song mapFromSongRequestToSong(final SongRequestDto songDto) {
		return Song.builder()
		           .duration(songDto.duration())
		           .name(songDto.name())
		           .build();
	}

	static Song mapFromSongRequestDtoToSong(final SongRequestDto songRequestDto) {
		SongLanguage language = SongLanguage.valueOf(songRequestDto.songLanguageDto()
		                                                           .name());
		return Song.builder()
		           .name(songRequestDto.name())
		           .releaseDate(songRequestDto.releaseDate())
		           .duration(songRequestDto.duration())
		           .language(language)
		           .build();
	}
}