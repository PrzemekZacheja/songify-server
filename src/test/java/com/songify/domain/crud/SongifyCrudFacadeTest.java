package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfig.createSongifyCrudFacade(new InMemorySongRepository(),
                                                                                          new InMemoryArtistRepository(),
                                                                                          new InMemoryGenreRepository(),
                                                                                          new InMemoryAlbumRepository());


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

    @Test
    @DisplayName("should add Album and return correct name of Album and release date, but he must contain at least one Song")
    void add_Album() {
        //given
        Instant now = Instant.now();
        SongDto songDto = songifyCrudFacade.addSong(SongRequestDto.builder()
                                                                  .name("song")
                                                                  .duration(10L)
                                                                  .songLanguageDto(SongLanguageDto.ENGLISH)
                                                                  .releaseDate(now)
                                                                  .build());
        AlbumRequestDto albumRequestDto = new AlbumRequestDto("album", now, songDto.id());
        //when
        AlbumDto response = songifyCrudFacade.addAlbumWithSongs(albumRequestDto);
        //then
        assertThat(response.title()).isEqualTo("album");
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(response.id())
                                    .getReleaseDate()).isEqualTo(now);
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(response.id())
                                    .getSongs()).size()
                                                .isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw exception when artist not found while artis is deleting")
    void should_throw_exception_when_artist_not_found_while_artis_is_deleting() {
        //given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(0L));
        //then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with id " + 0 + " not found");
    }

    @Test
    @DisplayName("Should delete Genre and return empty list")
    void delete_Genre() {
        //given
        GenreDto genreDto = songifyCrudFacade.addGenre(new GenreRequestDto("rock"));
        assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).isNotEmpty();
        //when
        songifyCrudFacade.deleteGenreById(genreDto.id());
        //then
        assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist when he have no albums")
    void should_delete_artist_when_he_have_no_albums() {
        //given
        ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        assertThat(songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistDtoU2.id()));
        //then
        assertThat(throwable).isNull();
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist with album and songs by id when artist have one album and that album have only one artist")
    void should_delete_artist_with_album_and_songs_by_id_when_artist_have_one_album_and_that_album_have_only_one_artist() {
        //given
        ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH));
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), songDto.id()));
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto.id());
        Set<AlbumDto> albumsDtoByArtistId = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id());
        assertThat(albumsDtoByArtistId).isNotEmpty();
        assertThat(albumsDtoByArtistId.size()).isEqualTo(1);
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistDtoU2.id()));
        //then
        assertThat(throwable).isNull();
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id())).isEmpty();
    }

    @Test
    @DisplayName("Should delete only artist without album and songs by id when artist have more than one album and that album have more than one artist")
    void should_delete_only_artist_without_album_and_songs_by_id_when_artist_have_more_than_one_album_and_that_album_have_more_than_one_artist() {
        //given
        ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        ArtistDto artistDtoNirvana = songifyCrudFacade.addArtist(new ArtistRequestDto("Nirvana"));
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH));
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), songDto.id()));
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto.id());
        songifyCrudFacade.addArtistToAlbum(artistDtoNirvana.id(), albumDto.id());
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())
                                    .size()).isEqualTo(2);
        Set<AlbumDto> albumsDtoByArtistId = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id());
        assertThat(albumsDtoByArtistId).isNotEmpty();
        assertThat(albumsDtoByArtistId.size()).isEqualTo(1);
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistDtoU2.id()));
        //then
        assertThat(throwable).isNull();
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())
                                    .size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoNirvana.id())
                                    .size()).isEqualTo(1);
    }
}