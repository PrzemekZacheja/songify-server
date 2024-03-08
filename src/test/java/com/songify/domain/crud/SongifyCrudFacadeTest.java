package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfig.createSongifyCrudFacade(
            new InMemorySongRepository(),
            new InMemoryArtistRepository(),
            new InMemoryGenreRepository(),
            new InMemoryAlbumRepository()
                                                                                         );


    @Test
    void should_add_Artist_and_return_notNull_value_of_U2_name() {
        //given
        ArtistRequestDto u2 = new ArtistRequestDto("U2");
        //when
        ArtistDto response = songifyCrudFacade.addArtist(u2);
        //then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("U2");
    }

    @Test
    void should_add_Artist_and_return_notNull_value_of_nirvana_name() {
        //given
        ArtistRequestDto nirvana = new ArtistRequestDto("Nirvana");
        //when
        ArtistDto response = songifyCrudFacade.addArtist(nirvana);
        //then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("Nirvana");
    }
}