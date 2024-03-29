package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistWithAlbumsDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
class ArtistProvider {

	private final ArtistRepository repository;
	private final AlbumProvider albumProvider;

	Artist findById(final Long id) {
		return repository.findById(id)
		                 .orElseThrow(() -> new ArtistNotFoundException(id));

	}

	Set<ArtistWithAlbumsDto> findAllArtistsWithAlbumsDto(final Pageable pageable) {
		Set<ArtistDto> allArtistsDto = findAllArtists(pageable);
		log.info("Found {} artists", allArtistsDto.size());
		return allArtistsDto.stream()
		                    .map(artistDto -> ArtistWithAlbumsDto.builder()
		                                                         .id(artistDto.id())
		                                                         .name(artistDto.name())
		                                                         .albums(albumProvider.findAlbumsDtoByArtistId(artistDto.id()))
		                                                         .build())
		                    .collect(Collectors.toSet());
	}

	Set<ArtistDto> findAllArtists(final Pageable pageable) {
		log.info("Finding all artists");
		return repository.findAll(pageable)
		                 .stream()
		                 .map(artist ->
				                      ArtistDto.builder()
				                               .id(artist.getId())
				                               .name(artist.getName())
				                               .build()
		                     )
		                 .collect(Collectors.toSet());
	}

	Set<ArtistDto> findArtistsDtoByAlbumId(final Long id) {
		AlbumInfo albumByIdWithArtistsAndSongs = albumProvider.findAlbumByIdWithArtistsAndSongs(id);
		if (albumByIdWithArtistsAndSongs != null) {
			return ArtistMapper.mapArtistInfosToArtistDtos(albumByIdWithArtistsAndSongs.getArtists());
		}
		return Collections.emptySet();
	}
}