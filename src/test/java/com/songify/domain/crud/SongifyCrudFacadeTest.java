package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.ArtistWithAlbumsDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.GenreWithSongsDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Collections;
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
		songifyCrudFacade.addArtist(u2);
		Set<ArtistDto> allArtistsDto = songifyCrudFacade.findAllArtistsDto(Pageable.unpaged());
		//then
		assertThat(allArtistsDto).isNotNull();
		assertThat(allArtistsDto.size()).isEqualTo(1);
		assertThat(allArtistsDto).extracting(ArtistDto::name)
		                         .contains("U2");
	}

	@Test
	@DisplayName("should add Artist and return correct name of Artist")
	void should_add_Artist_and_return_notNull_value_of_nirvana_name() {
		//given
		ArtistRequestDto nirvana = new ArtistRequestDto("Nirvana");
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
		//when
		songifyCrudFacade.addArtist(nirvana);
		//then
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
		                            .size()).isEqualTo(1);
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).extracting(ArtistDto::name)
		                                                                   .contains("Nirvana");
	}

	@Test
	@DisplayName("should add Artist with album and song, where album and song have default value")
	void should_add_Artist_with_album_and_song_where_album_and_song_have_default_value() {
		//given
		ArtistRequestDto u2 = new ArtistRequestDto("U2");
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
		Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistById(artistDtoU2.id()));
		//then
		assertThat(throwable).isNull();
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
	}

	@Test
	@DisplayName("Should delete artist with album and songs by id when artist have one album and that album have only one artist")
	void should_delete_artist_with_album_and_songs_by_id_when_artist_have_one_album_and_that_album_have_only_one_artist() {
		//given
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
		ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
		                            .size()).isEqualTo(1);
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).extracting(ArtistDto::name)
		                                                                   .containsOnly("U2");

		SongDto songDto1 = songifyCrudFacade.addSong(new SongRequestDto("SongName1", Instant.now(), 14L, SongLanguage.ENGLISH));
		SongDto songDto2 = songifyCrudFacade.addSong(new SongRequestDto("SongName2", Instant.now(), 14L, SongLanguage.ENGLISH));
		SongDto songDto3 = songifyCrudFacade.addSong(new SongRequestDto("SongName3", Instant.now(), 14L, SongLanguage.ENGLISH));
		SongDto songDto4 = songifyCrudFacade.addSong(new SongRequestDto("SongName4", Instant.now(), 14L, SongLanguage.ENGLISH));
		assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())
		                            .size()).isEqualTo(4);

		AlbumDto albumDto1 = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum1", Instant.now(), Set.of(songDto1.id(), songDto2.id())));
		AlbumDto albumDto2 = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum2", Instant.now(), Set.of(songDto3.id(), songDto4.id())));


		songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto1.id());
		songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto2.id());

		Set<AlbumDto> albumsDtoByArtistId = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id());
		assertThat(albumsDtoByArtistId).isNotEmpty();
		assertThat(albumsDtoByArtistId.size()).isEqualTo(2);
		assertThat(albumsDtoByArtistId).extracting(AlbumDto::title)
		                               .containsOnly("TitleAlbum1", "TitleAlbum2");
		//when
		songifyCrudFacade.deleteArtistById(artistDtoU2.id());
		//then
		assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isEmpty();
		assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())).isEmpty();
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
	}

	@Test
	@DisplayName("Should delete only artist without album and songs by id when artist have more than one album and that album have more than one artist")
	void should_delete_only_artist_without_album_and_songs_by_id_when_artist_have_more_than_one_album_and_that_album_have_more_than_one_artist() {
		//given
		ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
		ArtistDto artistDtoNirvana = songifyCrudFacade.addArtist(new ArtistRequestDto("Nirvana"));
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
		AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), Collections.singleton(songDto.id())));
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
		songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto.id());
		songifyCrudFacade.addArtistToAlbum(artistDtoNirvana.id(), albumDto.id());
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())
		                            .size()).isGreaterThanOrEqualTo(2);
		Set<ArtistDto> artistsDtoByAlbumId = songifyCrudFacade.findArtistsDtoByAlbumId(albumDto.id());
		assertThat(artistsDtoByAlbumId).isNotEmpty();
		assertThat(artistsDtoByAlbumId.size()).isEqualTo(2);
		Set<AlbumDto> albumsDtoByArtistId = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id());
		assertThat(albumsDtoByArtistId).isNotEmpty();
		assertThat(albumsDtoByArtistId.size()).isEqualTo(1);
		//when
		Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistById(artistDtoU2.id()));
		//then
		assertThat(throwable).isNull();
		Set<ArtistDto> allArtistsDto = songifyCrudFacade.findAllArtistsDto(Pageable.unpaged());
		assertThat(allArtistsDto
				           .size()).isEqualTo(1);
		assertThat(allArtistsDto).extracting(ArtistDto::name)
		                         .containsOnly("Nirvana");
		assertThat(songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoNirvana.id())
		                            .size()).isEqualTo(1);
	}

	@Test
	@DisplayName("Should throw exception when artist not found while artis is deleting")
	void should_throw_exception_when_artist_not_found_while_artis_is_deleting() {
		//given
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isEmpty();
		//when
		Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistById(0L));
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
	@DisplayName("you can assign artists to albums (an album can have more artists, an artist can have several albums")
	void can_assign_artists_to_albums_an_album_can_have_more_artists_an_artist_can_have_several_albums() {
		//given
		ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
		ArtistDto artistDtoNirvana = songifyCrudFacade.addArtist(new ArtistRequestDto("Nirvana"));
		SongDto songDto1 = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 4L, SongLanguage.ENGLISH));
		SongDto songDto2 = songifyCrudFacade.addSong(new SongRequestDto("SongName2", Instant.now(), 4L, SongLanguage.ENGLISH));
		AlbumDto albumDto1 = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), Collections.singleton(songDto1.id())));
		AlbumDto albumDto2 = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum2", Instant.now(), Collections.singleton(songDto2.id())));
		assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto1.id())).extracting(albumInfo -> albumInfo.getArtists()
		                                                                                                                .size())
		                                                                              .isEqualTo(0);
		assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto2.id())).extracting(albumInfo -> albumInfo.getArtists()
		                                                                                                                .size())
		                                                                              .isEqualTo(0);
		//when
		songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto1.id());
		songifyCrudFacade.addArtistToAlbum(artistDtoNirvana.id(), albumDto1.id());
		songifyCrudFacade.addAlbumToArtist(albumDto2.id(), artistDtoU2.id());
		//then
		assertThat(songifyCrudFacade.findAllArtistsDto(Pageable.unpaged())).isNotEmpty();
		assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto1.id())
		                            .getArtists()
		                            .size()).isEqualTo(2);
		assertThat(songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id())
		                            .size()).isEqualTo(2);
	}

	@Test
	@DisplayName("should return all artists")
	void should_return_all_artists() {
		//given
		songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
		songifyCrudFacade.addArtist(new ArtistRequestDto("Nirvana"));
		//when
		Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtistsDto(Pageable.unpaged());
		//then
		assertThat(allArtists.size()).isEqualTo(2);
		assertThat(allArtists.stream()
		                     .filter(artistDto -> artistDto.name()
		                                                   .contains("U2"))
		                     .collect(Collectors.toSet())
		                     .size()).isEqualTo(1);
		assertThat(allArtists.stream()
		                     .filter(artistDto -> artistDto.name()
		                                                   .contains("Nirvana"))
		                     .collect(Collectors.toSet())
		                     .size()).isEqualTo(1);
	}

	@Test
	@DisplayName("should return all artists with albums")
	void should_return_all_artists_with_albums() {
		//given
		ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
		ArtistDto artistDtoNirvana = songifyCrudFacade.addArtist(new ArtistRequestDto("Nirvana"));
		SongDto songDto1 = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 4L, SongLanguage.ENGLISH));
		SongDto songDto2 = songifyCrudFacade.addSong(new SongRequestDto("SongName2", Instant.now(), 4L, SongLanguage.ENGLISH));
		AlbumDto albumDto1 = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), Collections.singleton(songDto1.id())));
		AlbumDto albumDto2 = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum2", Instant.now(), Collections.singleton(songDto2.id())));
		songifyCrudFacade.addAlbumToArtist(albumDto1.id(), artistDtoU2.id());
		songifyCrudFacade.addAlbumToArtist(albumDto2.id(), artistDtoNirvana.id());
		//when
		Set<ArtistWithAlbumsDto> allArtistsWithAlbums = songifyCrudFacade.findAllArtistsDtoWithAlbumsDto(Pageable.unpaged());
		//then
		assertThat(allArtistsWithAlbums.size()).isEqualTo(2);
		assertThat(allArtistsWithAlbums.stream()
		                               .filter(artistWithAlbumsDto -> artistWithAlbumsDto.name()
		                                                                                 .contains("U2"))
		                               .collect(Collectors.toSet())
		                               .size()).isEqualTo(1);
		assertThat(allArtistsWithAlbums.stream()
		                               .filter(artistWithAlbumsDto -> artistWithAlbumsDto.name()
		                                                                                 .contains("Nirvana"))
		                               .collect(Collectors.toSet())
		                               .size()).isEqualTo(1);
		Set<AlbumDto> albumByArtistU2 = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoU2.id());
		assertThat(albumByArtistU2.size()).isEqualTo(1);
		assertThat(albumByArtistU2.stream()
		                          .filter(albumDto -> albumDto.title()
		                                                      .contains("TitleAlbum"))
		                          .collect(Collectors.toSet())
		                          .size()).isEqualTo(1);
		Set<AlbumDto> albumByArtistNirvana = songifyCrudFacade.findAlbumsDtoByArtistId(artistDtoNirvana.id());
		assertThat(albumByArtistNirvana.size()).isEqualTo(1);
		assertThat(albumByArtistNirvana.stream()
		                               .filter(albumDto -> albumDto.title()
		                                                           .contains("TitleAlbum2"))
		                               .collect(Collectors.toSet())
		                               .size()).isEqualTo(1);
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
	@DisplayName("should throw exception when song not found by id")
	void should_throw_exception_when_song_not_found_by_id() {
		//given
		assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isEmpty();
		//when
		Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(0L));
		//then
		assertThat(throwable).isInstanceOf(SongNotFoundException.class);
		assertThat(throwable.getMessage()).isEqualTo("Song with id " + 0 + " not found");
	}

	@Test
	@DisplayName("should add Song and return correct name of Song and not null id")
	void should_add_Song_and_return_correct_name_od_Song_and_not_null_id() {
		//given
		SongRequestDto songRequestDto = new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH);
		List<SongDto> allSongsBeforeAddingSong = songifyCrudFacade.findAllSongsDto(Pageable.unpaged());
		assertThat(allSongsBeforeAddingSong).isEmpty();
		//when
		songifyCrudFacade.addSong(songRequestDto);
		List<SongDto> allSongsDto = songifyCrudFacade.findAllSongsDto(Pageable.unpaged());
		//then
		assertThat(allSongsDto).isNotNull();
		assertThat(allSongsDto).extracting(SongDto::id)
		                       .isNotNull();
		assertThat(allSongsDto).extracting(SongDto::id)
		                       .contains(0L);
		assertThat(allSongsDto).extracting(SongDto::name)
		                       .contains("SongName");
	}

	@Test
	@DisplayName("Songs can only be assigned to albums")
	void songs_can_only_be_assigned_to_albums() {
		//given
		SongRequestDto songRequestDto = new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH);
		List<SongDto> allSongs = songifyCrudFacade.findAllSongsDto(Pageable.unpaged());
		assertThat(allSongs).isEmpty();
		SongDto addedSong = songifyCrudFacade.addSong(songRequestDto);
		AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("AlbumName", Instant.now(), Collections.singleton(addedSong.id())));
		//when
		SongDto secondSong = songifyCrudFacade.addSong(new SongRequestDto("SecondSong", Instant.now(), 4L, SongLanguage.ENGLISH));
		Throwable throwable = catchThrowable(() -> songifyCrudFacade.addSongToAlbum(secondSong.id(), albumDto.id()));
		//then
		assertThat(throwable).isNull();
		assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
		                            .getSongs()
		                            .size()).isEqualTo(2);
	}

	@Test
	@DisplayName("should delete song by Id and return empty list")
	void should_delete_song_by_Id_and_return_empty_list() {
		//given
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
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
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
		AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), Collections.singleton(songDto.id())));
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

	@Test
	@DisplayName("should edit Song name and duration")
	void should_edit_Song_name_and_duration() {
		//given
		Instant releaseDate = Instant.now();
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", releaseDate, 14L, SongLanguage.ENGLISH));
		//when
		SongDto editedNameOfSOng = songifyCrudFacade.updateSongNameById(songDto.id(), "SongNameEdit");
		SongDto editedDurationOfSong = songifyCrudFacade.updateSongDurationById(editedNameOfSOng.id(), 4L);
		//then
		assertThat(editedNameOfSOng.name()).isEqualTo("SongNameEdit");
		assertThat(editedDurationOfSong.duration()).isEqualTo(4L);
	}

	@Test
	@DisplayName("you can assign songs to an artist (via album)")
	void you_can_assign_songs_to_an_artist_via_album() {
		//given
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
		AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("TitleAlbum", Instant.now(), Collections.singleton(songDto.id())));
		ArtistDto artistDto = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
		//when
		songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
		songifyCrudFacade.addSongToAlbum(songDto.id(), albumDto.id());
		//then
		assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
		                            .getArtists()
		                            .size()).isEqualTo(1);
		assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id())
		                            .getSongs()
		                            .size()).isEqualTo(1);
	}

	@Test
	@DisplayName("should return all songs")
	void should_return_all_songs() {
		//given
		songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
		songifyCrudFacade.addSong(new SongRequestDto("SongName2", Instant.now(), 14L, SongLanguage.ENGLISH));
		//when
		List<SongDto> allSongs = songifyCrudFacade.findAllSongsDto(Pageable.unpaged());
		//then
		assertThat(allSongs).isNotEmpty();
		assertThat(allSongs.size()).isEqualTo(2);
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
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
		songifyCrudFacade.addGenreToSong(genreDto.id(), songDto.id());
		assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).isNotEmpty();
		//when
		songifyCrudFacade.deleteGenreById(genreDto.id());
		//then
		assertThat(songifyCrudFacade.findAllGenres(Pageable.unpaged())).isEmpty();
		assertThat(songifyCrudFacade.findAllSongsDto(Pageable.unpaged())).isEmpty();
	}

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
	@DisplayName("should not add Genre to Song when it already contains this Genre")
	void should_not_add_Genre_to_Song_when_it_already_contains_this_Genre() {
		//given
		GenreDto rock = songifyCrudFacade.addGenre(new GenreRequestDto("rock"));
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
		songifyCrudFacade.addGenreToSong(rock.id(), songDto.id());
		GenreDto pop = songifyCrudFacade.addGenre(new GenreRequestDto("pop"));
		//when
		Throwable throwable = catchThrowable(() -> songifyCrudFacade.addGenreToSong(pop.id(), songDto.id()));
		//then
		assertThat(throwable).isInstanceOf(NotAddGenreToSongException.class);
	}

	@Test
	@DisplayName("should return default genre when song doesn't have genre")
	void should_return_default_genre_when_song_does_not_have_genre() {
		//given
		SongDto songDto = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));
		//when
		GenreDto genreDtoById = songifyCrudFacade.findGenreBySongId(songDto.id());
		//then
		assertThat(genreDtoById.name()).isEqualTo("default");
	}

	@Test
	@DisplayName("should return all genres")
	void should_return_all_genres() {
		//given
		songifyCrudFacade.addGenre(new GenreRequestDto("rock"));
		songifyCrudFacade.addGenre(new GenreRequestDto("pop"));
		//when
		Set<GenreDto> allGenres = songifyCrudFacade.findAllGenres(Pageable.unpaged());
		//then
		assertThat(allGenres).isNotEmpty();
		assertThat(allGenres.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("should return genre with songs")
	void should_return_genre_with_songs() {
		//given
		GenreDto rock = songifyCrudFacade.addGenre(new GenreRequestDto("rock"));
		GenreDto pop = songifyCrudFacade.addGenre(new GenreRequestDto("pop"));
		SongDto songDto1 = songifyCrudFacade.addSong(new SongRequestDto("SongName2", Instant.now(), 14L, SongLanguage.ENGLISH));
		SongDto songDto2 = songifyCrudFacade.addSong(new SongRequestDto("SongName3", Instant.now(), 14L, SongLanguage.ENGLISH));
		SongDto songDto3 = songifyCrudFacade.addSong(new SongRequestDto("SongName4", Instant.now(), 14L, SongLanguage.ENGLISH));
		SongDto songDto4 = songifyCrudFacade.addSong(new SongRequestDto("SongName", Instant.now(), 14L, SongLanguage.ENGLISH));

		songifyCrudFacade.addGenreToSong(rock.id(), songDto1.id());
		songifyCrudFacade.addGenreToSong(rock.id(), songDto2.id());
		songifyCrudFacade.addGenreToSong(pop.id(), songDto3.id());
		songifyCrudFacade.addGenreToSong(pop.id(), songDto4.id());
		//when
		GenreWithSongsDto genreRockWithSongs = songifyCrudFacade.findGenreWithSongs(rock.id());
		GenreWithSongsDto genrePopWithSongs = songifyCrudFacade.findGenreWithSongs(pop.id());
		//then
		assertThat(genreRockWithSongs.songs()
		                             .size()).isEqualTo(2);
		assertThat(genrePopWithSongs.songs()
		                            .size()).isEqualTo(2);
		assertThat(genreRockWithSongs.songs()
		                             .stream()
		                             .map(SongDto::name)
		                             .collect(Collectors.toList())).containsAnyOf("SongName2", "SongName3");
		assertThat(genrePopWithSongs.songs()
		                            .stream()
		                            .map(SongDto::name)
		                            .collect(Collectors.toList())).containsAnyOf("SongName4", "SongName");
	}

	@Test
	@DisplayName("should add Album and return correct name of Album, but he must contain at least one Song")
	void add_Album() {
		//given
		Instant now = Instant.now();
		SongDto songDto = songifyCrudFacade.addSong(SongRequestDto.builder()
		                                                          .name("song")
		                                                          .duration(10L)
		                                                          .songLanguage(SongLanguage.ENGLISH)
		                                                          .releaseDate(now)
		                                                          .build());
		AlbumRequestDto albumRequestDto = new AlbumRequestDto("album", now, Collections.singleton(songDto.id()));
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
		                                                          .songLanguage(SongLanguage.ENGLISH)
		                                                          .releaseDate(now)
		                                                          .build());
		AlbumRequestDto albumRequestDto = new AlbumRequestDto("album", now, Collections.singleton(songDto.id()));
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
		                                                           .songLanguage(SongLanguage.ENGLISH)
		                                                           .releaseDate(now)
		                                                           .build());
		AlbumRequestDto albumRequestDto = new AlbumRequestDto("album", now, Collections.singleton(songDto1.id()));
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
		                                                           .songLanguage(SongLanguage.ENGLISH)
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

	@Test
	@DisplayName("should return all albums")
	void should_return_all_albums() {
		//given
		Instant now = Instant.now();
		SongDto songDto1 = songifyCrudFacade.addSong(SongRequestDto.builder()
		                                                           .name("song")
		                                                           .duration(10L)
		                                                           .songLanguage(SongLanguage.ENGLISH)
		                                                           .releaseDate(now)
		                                                           .build());
		songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("album", now, Collections.singleton(songDto1.id())));

		SongDto songDto2 = songifyCrudFacade.addSong(SongRequestDto.builder()
		                                                           .name("song2")
		                                                           .duration(10L)
		                                                           .songLanguage(SongLanguage.ENGLISH)
		                                                           .releaseDate(now)
		                                                           .build());
		songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("album2", now, Collections.singleton(songDto2.id())));
		//when
		Set<AlbumDto> allAlbums = songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged());
		//then
		assertThat(allAlbums).isNotEmpty();
		assertThat(allAlbums.size()).isEqualTo(2);
		assertThat(allAlbums.stream()
		                    .anyMatch(albumDto1 -> albumDto1.title()
		                                                    .equals("album"))).isTrue();
		assertThat(allAlbums.stream()
		                    .anyMatch(albumDto1 -> albumDto1.title()
		                                                    .equals("album2"))).isTrue();
	}

	@Test
	@DisplayName("should return all albums with artists and songs")
	void should_return_all_albums_with_artists_and_songs() {
		//given
		Instant now = Instant.now();
		SongDto songDto1 = songifyCrudFacade.addSong(SongRequestDto.builder()
		                                                           .name("song")
		                                                           .duration(10L)
		                                                           .songLanguage(SongLanguage.ENGLISH)
		                                                           .releaseDate(now)
		                                                           .build());
		AlbumDto albumDto = songifyCrudFacade.addAlbumWithSongs(new AlbumRequestDto("album", now, Collections.singleton(songDto1.id())));
		ArtistDto artistDtoU2 = songifyCrudFacade.addArtist(new ArtistRequestDto("U2"));
		songifyCrudFacade.addArtistToAlbum(artistDtoU2.id(), albumDto.id());
		//when
		AlbumInfo albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
		//then
		assertThat(albumByIdWithArtistsAndSongs.getTitle()).isEqualTo("album");
		assertThat(albumByIdWithArtistsAndSongs.getArtists()).size()
		                                                     .isEqualTo(1);
		assertThat(albumByIdWithArtistsAndSongs.getArtists()
		                                       .stream()
		                                       .map(AlbumInfo.ArtistInfo::getName)
		                                       .collect(Collectors.toSet())).contains("U2");
		assertThat(albumByIdWithArtistsAndSongs.getSongs()).size()
		                                                   .isEqualTo(1);
		assertThat(albumByIdWithArtistsAndSongs.getSongs()
		                                       .stream()
		                                       .map(AlbumInfo.SongInfo::getName)
		                                       .collect(Collectors.toSet())).contains("song");
	}

	@Test
	@DisplayName("should throw exception when album not found by id")
	void should_throw_exception_when_album_not_found_by_id() {
		//given
		assertThat(songifyCrudFacade.findAllAlbumsDto(Pageable.unpaged())
		                            .isEmpty());
		//when
		Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumDtoById(10L));
		//then
		assertThat(throwable).isNotNull();
		assertThat(throwable).isInstanceOf(AlbumNotFoundException.class);
	}

	@Test
	@DisplayName("should update Song and return correct values")
	void should_update_Song_and_return_correct_values() {
		//given
		Instant now = Instant.now();
		SongDto songDto = songifyCrudFacade.addSong(SongRequestDto.builder()
		                                                          .name("song")
		                                                          .duration(10L)
		                                                          .songLanguage(SongLanguage.ENGLISH)
		                                                          .releaseDate(now)
		                                                          .build());
		//when
		songifyCrudFacade.updateSongById(songDto.id(), new SongRequestDto("song2", now, 10L, SongLanguage.ENGLISH));
		List<SongDto> allSongsDto = songifyCrudFacade.findAllSongsDto(Pageable.unpaged());
		//then
		assertThat(allSongsDto).isNotEmpty();
		assertThat(allSongsDto.size()).isEqualTo(1);
		assertThat(allSongsDto).extracting(SongDto::name)
		                       .contains("song2");
	}

	@Test
	@DisplayName("should return Song by id")
	void should_return_Song_by_id() {
		//given
		Instant now = Instant.now();
		SongDto songDto = songifyCrudFacade.addSong(SongRequestDto.builder()
		                                                          .name("song")
		                                                          .duration(10L)
		                                                          .songLanguage(SongLanguage.ENGLISH)
		                                                          .releaseDate(now)
		                                                          .build());
		//when
		SongDto songDtoById = songifyCrudFacade.findSongDtoById(songDto.id());
		//then
		assertThat(songDtoById.name()).isEqualTo("song");
		assertThat(songDtoById.duration()).isEqualTo(10L);
		assertThat(songDtoById.language()).isEqualTo(SongLanguage.ENGLISH);
	}
}