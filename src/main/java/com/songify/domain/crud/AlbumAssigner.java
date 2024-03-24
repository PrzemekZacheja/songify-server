package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
class AlbumAssigner {

	private final AlbumProvider albumProvider;
	private final ArtistProvider artistProvider;

	void addAlbumToArtist(final Long albumId, final Long artistId) {
		Album album = albumProvider.findById(albumId);
		Artist artist = artistProvider.findById(artistId);
		album.addArtist(artist);
		log.info("Album id " + album.getId() + " added to artist " + artist.getName());
	}
}