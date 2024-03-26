package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreWithSongsDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
class GenreProvider {

    private final GenreRepository genreRepository;
    private final SongProvider songProvider;

	Set<GenreDto> findAll(final Pageable pageable) {
		log.info("Getting all genres");
        return genreRepository.findAll(pageable)
                              .stream()
                              .map(GenreMapper::mapFromGenreToGenreDto)
                              .collect(Collectors.toSet());
    }

    Genre findGenreById(final Long genreId) {
        return genreRepository.findById(genreId)
                              .orElseThrow(
                                      () -> new GenreNotFoundException("Genre with id: " + genreId + " not found")
                                          );
    }

    GenreDto findGenreBySongId(final Long id) {
        Song song = songProvider.findSongById(id);
	    log.info("Getting genre by song id: {}", id);
        return song.getGenre() == null ? null : GenreMapper.mapFromGenreToGenreDto(song.getGenre());
    }

	GenreWithSongsDto findGenreWithSongs(final Long id) {
		Genre genre = findGenreById(id);
		if (genre != null) {
			return GenreMapper.mapFromGenreToGenreWithSongsDto(genre, songProvider.findSongsByGenreId(genre.getId()));
		}
		log.info("Genre with id: {} not found", id);
		return GenreWithSongsDto.builder()
		                        .build();
	}
}