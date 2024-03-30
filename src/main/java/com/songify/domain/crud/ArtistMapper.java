package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.ArtistDto;

import java.util.Set;
import java.util.stream.Collectors;

class ArtistMapper {
	static Set<ArtistDto> mapArtistInfosToArtistDtos(final Set<AlbumInfo.ArtistInfo> artists) {
		return artists.stream()
		              .map(ArtistMapper::mapArtistInfosToArtistDtos)
		              .collect(Collectors.toSet());
	}

	static ArtistDto mapArtistInfosToArtistDtos(final AlbumInfo.ArtistInfo artist) {
		return new ArtistDto(artist.getId(), artist.getName());
	}
}