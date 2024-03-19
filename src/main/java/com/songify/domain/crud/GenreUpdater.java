package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j2
class GenreUpdater {

    GenreRepository genreRepository;
    GenreProvider genreProvider;

    GenreDto updateNameOfGenreById(final Long id, final String newNameOfGenre) {
        log.info("Updating name of genre with id: " + id);
        try {
            Genre genreById = genreProvider.findGenreById(id);
            genreRepository.updateNameById(newNameOfGenre, id);
            log.info("Genre with id: " + id + " updated");
            return GenreMapper.mapFromGenreToGenreDto(genreById);
        } catch (GenreNotFoundException e) {
            log.error("Genre with id: " + id + " not found");
        }
        return null;
    }
}