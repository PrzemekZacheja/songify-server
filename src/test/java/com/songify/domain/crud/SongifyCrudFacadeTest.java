package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfig.createSongifyCrudFacade(
            new InMemorySongRepository(),
            new InMemoryArtistRepository(),
            new InMemoryGenreRepository(),
            new InMemoryAlbumRepository()
                                                                                         );


    @Test
    @DisplayName("should add Artist and return correct name of Artist and not null id")
    void should_add_Artist_and_return_notNull_value_and_U2_name() {
        //given
        ArtistRequestDto u2 = new ArtistRequestDto("U2");
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        //when
        ArtistDto response = songifyCrudFacade.addArtist(u2);
        //then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("U2");
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())
                                    .size()).isEqualTo(1);
    }

    @Test
    @DisplayName("should add Artist and return correct name of Artist")
    void should_add_Artist_and_return_notNull_value_of_nirvana_name() {
        //given
        ArtistRequestDto nirvana = new ArtistRequestDto("Nirvana");
        //when
        ArtistDto response = songifyCrudFacade.addArtist(nirvana);
        //then
        assertThat(response.name()).isEqualTo("Nirvana");
    }

    @Test
    @DisplayName("should not add Artist when sent name is empty and return null")
    void should_not_add_song_when_sent_name_is_empty_and_return_null() {
        //given
        ArtistRequestDto emptyName = new ArtistRequestDto("");
        //when
        ArtistDto artistDto = songifyCrudFacade.addArtist(emptyName);
        //then
        assertThat(artistDto).isNull();
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())
                                    .size()).isEqualTo(0);
    }

    @Test
    @DisplayName("should add Genre and return correct name of Genre and not null id")
    void add_Genre() {
        //given
        String genreName = "rock";
        Set<GenreDto> allGenres = songifyCrudFacade.findAllGenres(Pageable.unpaged());
        assertThat(allGenres).isEmpty();
        //when
        var response = songifyCrudFacade.addGenre(new GenreRequestDto(genreName));
        //then
        assertThat(response.name()).isEqualTo("rock");
        assertThat(response.id()).isNotNull();
        assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).size()
                                                                       .isEqualTo(1);
    }

    @Test
    @DisplayName("should not add Genre when sent name is empty and return null")
    void add_Genre_when_sent_name_is_empty() {
        //given
        String genreName = "";
        //when
        var response = songifyCrudFacade.addGenre(new GenreRequestDto(genreName));
        //then
        assertThat(response).isNull();
        assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).size()
                                                                       .isEqualTo(0);
    }
}