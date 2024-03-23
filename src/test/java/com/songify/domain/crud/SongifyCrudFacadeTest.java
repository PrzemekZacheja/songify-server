package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SongifyCrudFacadeTest {

    final SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfig.createSongifyCrudFacade(new InMemorySongRepository(),
                                                                                                new InMemoryArtistRepository(),
                                                                                                new InMemoryGenreRepository(),
                                                                                                new InMemoryAlbumRepository());


    @Test
    @DisplayName("should add Artist and return correct name of Artist and not null id")
    void should_add_Artist_and_return_notNull_value_and_U2_name() {
        //given
        ArtistRequestDto u2 = new ArtistRequestDto("U2");
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtistsDto(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        //when
        ArtistDto response = songifyCrudFacade.addArtist(u2);
        //then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("U2");
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
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
    @DisplayName("should add Artist with album and song, where album and song have default value")
    void should_add_Artist_with_album_and_song_where_album_and_song_have_default_value() {
        //given
        ArtistRequestDto u2 = new ArtistRequestDto("U2");
        AlbumRequestDto albumRequestDto = new AlbumRequestDto("AlbumName", Instant.now(), null);
        SongRequestDto songRequestDto = new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH);
        //when
        ArtistDto artistDto = songifyCrudFacade.addArtistWithDefaultAlbumAndSongs(u2);
        //then
        assertThat(artistDto.name()).isEqualTo("U2");
        Set<AlbumDto> allAlbums = songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged());
        assertThat(allAlbums.size()).isEqualTo(1);
        assertThat(allAlbums.stream()
                            .filter(albumDto -> albumDto.title()
                                                        .contains("Default Album :"))
                            .collect(Collectors.toSet())
                            .size()).isEqualTo(1);
        List<SongDto> allSongs = songifyCrudFacade.findAllSongsDto(Pageable.unpaged());
        assertThat(allSongs.size()).isEqualTo(1);
        assertThat(allSongs.stream()
                           .filter(songDto -> songDto.name()
                                                     .contains("Default Song :"))
                           .collect(Collectors.toSet())
                           .size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should delete artist when he have no albums")
    void should_delete_artist_when_he_have_no_albums() {
        //given
        ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
        assertThat(songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistDtoU2.id()));
        //then
        assertThat(throwable).isNull();
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist with album and songs by id when artist have one album and that album have only one artist")
    void should_delete_artist_with_album_and_songs_by_id_when_artist_have_one_album_and_that_album_have_only_one_artist() {
        //given
        ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH));
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), songDto.id()));
        songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto.id());
        Set<AlbumDto> albumsDtoByArtistId = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id());
        assertThat(albumsDtoByArtistId).isNotEmpty();
        assertThat(albumsDtoByArtistId.size()).isEqualTo(1);
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistDtoU2.id()));
        //then
        assertThat(throwable).isNull();
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
        Throwable throwableFindAlbums = catchThrowable(() -> songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
                                                                              .getSongs());
        assertThat(throwableFindAlbums).isNotNull();
        assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete only artist without album and songs by id when artist have more than one album and that album have more than one artist")
    void should_delete_only_artist_without_album_and_songs_by_id_when_artist_have_more_than_one_album_and_that_album_have_more_than_one_artist() {
        //given
        ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        ArtistDto artistDtoNirvana = songifyCrudFacade.addArtist(new ArtistRequestDto("Nirvana"));
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH));
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), songDto.id()));
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
        songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto.id());
        songifyCrudFacade.addArtistToAlbum(artistDtoNirvana.id(), albumDto.id());
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
                                    .size()).isGreaterThanOrEqualTo(2);
        Set<AlbumDto> albumsDtoByArtistId = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id());
        assertThat(albumsDtoByArtistId).isNotEmpty();
        assertThat(albumsDtoByArtistId.size()).isEqualTo(1);
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistDtoU2.id()));
        //then
        assertThat(throwable).isNull();
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
                                    .size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoNirvana.id())
                                    .size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should throw exception when artist not found while artis is deleting")
    void should_throw_exception_when_artist_not_found_while_artis_is_deleting() {
        //given
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
        //when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(0L));
        //then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with id " + 0 + " not found");
    }

    @Test
    @DisplayName("Should edit artist name")
    void should_edit_artist_name() {
        //given
        ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
        //when
        ArtistDto response = songifyCrudFacade.updateArtistNameById(artistDtoU2.id(), "U2 Edit");
        //then
        assertThat(response.name()).isEqualTo("U2 Edit");
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
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
                                    .size()).isEqualTo(0);
    }

    @Test
    @DisplayName("should add Song and return correct name of Song and not null id")
    void should_add_Song_and_return_correct_name_od_Song_and_not_null_id() {
        //given
        SongRequestDto songRequestDto = new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH);
        List<SongDto> allSongs = songifyCrudFacade.findAllSongsDto(Pageable.unpaged());
        assertThat(allSongs).isEmpty();
        //when
        SongDto response = songifyCrudFacade.addSong(songRequestDto);
        //then
        assertThat(response).isNotNull();
        assertThat(response.id()).isNotNull();
        assertThat(response.name()).isEqualTo("SongName");
    }

    @Test
    @DisplayName("should delete song by Id and return empty list")
    void should_delete_song_by_Id_and_return_empty_list() {
        //given
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH));
        assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isNotEmpty();
        //when
        songifyCrudFacade.deleteSongById(songDto.id());
        //then
        assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("should delete song by Id and and not delete Album and Artist when the Song was only one in Album")
    void should_delete_song_by_Id_and_and_not_delete_Album_and_Artist_when_the_Song_was_only_one_in_Album() {
        //given
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH));
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), songDto.id()));
        ArtistDto artistDto = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
        assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isNotEmpty();
        assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())).isNotEmpty();
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
        //when
        songifyCrudFacade.deleteSongById(songDto.id());
        //then
        assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())).isNotEmpty();
        AlbumInfo albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
        assertThat(albumByIdWithArtistsAndSongs.getTitle()).isEqualTo("TitleAlbum");
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
        assertThat(albumByIdWithArtistsAndSongs.getArtists()).size()
                                                             .isEqualTo(1);
    }

    //13. można edytować piosenkę (czas trwania, nazwę piosenki)
    @Test
    @DisplayName("should edit Song name and duration")
    void should_edit_Song_name_and_duration() {
        //given
        Instant releaseDate = Instant.now();
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", releaseDate, 14L, SongLanguageDto.ENGLISH));
        //when
        SongDto editedNameOfSOng = songifyCrudFacade.updateSongNameById(songDto.id(), "SongNameEdit");
        SongDto editedDurationOfSong = songifyCrudFacade.updateSongDurationById(editedNameOfSOng.id(), 4L);
        //then
        assertThat(editedNameOfSOng.name()).isEqualTo("SongNameEdit");
        assertThat(editedDurationOfSong.duration()).isEqualTo(4L);
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
    @DisplayName("should delete Genre and all songs if they contain this Genre")
    void should_delete_Genre_and_all_songs_if_they_contain_this_Genre() {
        //given
        GenreDto genreDto = songifyCrudFacade.addGenre(new GenreRequestDto("rock"));
        SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguageDto.ENGLISH));
        songifyCrudFacade.addGenreToSong(genreDto.id(), songDto.id());
        assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).isNotEmpty();
        //when
        songifyCrudFacade.deleteGenreById(genreDto.id());
        //then
        assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isEmpty();
    }

    //11. można edytować nazwę gatunku muzycznego
    @Test
    @DisplayName("should edit Genre name")
    void should_edit_Genre_name() {
        //given
        GenreDto genreDto = songifyCrudFacade.addGenre(new GenreRequestDto("rock"));
        assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).isNotEmpty();
        //when
        GenreDto response = songifyCrudFacade.updateGenreNameById(genreDto.id(), "rock edit");
        //then
        assertThat(response.name()).isEqualTo("rock edit");
    }

    @Test
    @DisplayName("should add Album and return correct name of Album, but he must contain at least one Song")
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
        assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())
                                    .size()).isEqualTo(1);
        assertThat(response.title()).isEqualTo("album");
        AlbumInfo albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(response.id());
        Set<AlbumInfo.SongInfo> songs = albumByIdWithArtistsAndSongs.getSongs();
        assertThat(songs).size()
                         .isEqualTo(1);
        boolean containsSong = songs.stream()
                                    .anyMatch(songInfo -> songInfo.getName()
                                                                  .equals("song"));
        assertThat(containsSong).isTrue();
    }

    @Test
    @DisplayName("should delete Album and return empty list only if Album contain any Songs")
    void delete_Album() {
        //given
        Instant now = Instant.now();
        SongDto songDto = songifyCrudFacade.addSong(SongRequestDto.builder()
                                                                  .name("song")
                                                                  .duration(10L)
                                                                  .songLanguageDto(SongLanguageDto.ENGLISH)
                                                                  .releaseDate(now)
                                                                  .build());
        AlbumRequestDto albumRequestDto = new AlbumRequestDto("album", now, songDto.id());
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(albumRequestDto);
        assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())
                                    .size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
                                    .getSongs()
                                    .size()).isEqualTo(1);
        //when
        songifyCrudFacade.deleteAlbumById(albumDto.id());
        //then
        assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())
                                    .size()).isEqualTo(1);
        songifyCrudFacade.deleteAlbumWithSongsById(albumDto.id());
        assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())
                                    .size()).isEqualTo(0);
        assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())
                                    .size()).isEqualTo(0);
    }

    @Test
    @DisplayName("should edit Album name, add Songs, add Artists")
    void should_edit_Album_name_add_Songs_add_Artists() {
        //given
        Instant now = Instant.now();
        SongDto songDto1 = songifyCrudFacade.addSong(SongRequestDto.builder()
                                                                  .name("song")
                                                                  .duration(10L)
                                                                  .songLanguageDto(SongLanguageDto.ENGLISH)
                                                                  .releaseDate(now)
                                                                  .build());
        AlbumRequestDto albumRequestDto = new AlbumRequestDto("album", now, songDto1.id());
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(albumRequestDto);
        ArtistDto artistDto1 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
        songifyCrudFacade.addArtistToAlbum(artistDto1.id(), albumDto.id());

        assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())
                                    .size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
                                    .size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
                                    .getSongs()
                                    .size()).isEqualTo(1);
        //when
        songifyCrudFacade.updateAlbumNameById(albumDto.id(), "album edit");

        SongDto songDto2 = songifyCrudFacade.addSong(SongRequestDto.builder()
                                                                   .name("song2")
                                                                   .duration(10L)
                                                                   .songLanguageDto(SongLanguageDto.ENGLISH)
                                                                   .releaseDate(now)
                                                                   .build());
        songifyCrudFacade.addSongToAlbum(songDto2.id(), albumDto.id());

        ArtistDto artistDto2 = songifyCrudFacade.addArtist(new ArtistRequestDto("Metallica"));
        songifyCrudFacade.addArtistToAlbum(artistDto2.id(), albumDto.id());
        //then
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
                                    .getTitle()).isEqualTo("album edit");
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
                                    .getArtists()).size()
                                                  .isEqualTo(2);
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
                                    .getSongs()).size()
                                                .isEqualTo(2);
    }
}