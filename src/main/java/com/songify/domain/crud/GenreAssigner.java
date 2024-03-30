package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
class GenreAssigner {

	private final SongProvider songProvider;
	private final GenreProvider genreProvider;

	void addGenreToSong(final Long genreId, final Long songId) {
		Song song = songProvider.findSongById(songId);
		Genre genre = genreProvider.findGenreById(genreId);
		String nameOfGenre = song.getGenre()
		                         .getName();
		if (!nameOfGenre.equals("default")) {
			throw new NotAddGenreToSongException("Song already has genre");
		}
		song.setGenre(genre);
		log.info("Song with id: {} has added genre: {}", songId, genreId);
	}
}