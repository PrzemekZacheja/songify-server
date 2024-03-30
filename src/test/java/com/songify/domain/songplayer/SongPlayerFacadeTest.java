package com.songify.domain.songplayer;

import com.songify.domain.crud.SongLanguage;
import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SongPlayerFacadeTest {

	SongifyCrudFacade songifyCrudFacade = mock(SongifyCrudFacade.class);
	SongPlayer songPlayer = mock(SongPlayer.class);
	SongPlayerFacade songPlayerFacade = new SongPlayerFacade(songifyCrudFacade, songPlayer);


	@Test
	@DisplayName("Should play song and return Success as String")
	void play_Song() {
		//given
		long songId = 1L;
		SongDto songDto = new SongDto(songId, "Song name", 3L, SongLanguage.ENGLISH);
		when(songifyCrudFacade.findSongDtoById(anyLong())).thenReturn(songDto);
		when(songPlayer.playByName(songDto.name())).thenReturn("Success");
		//when
		String result = songPlayerFacade.playSong(songId);
		//then
		assertThat(result).isEqualTo("Success");
	}

	@Test
	@DisplayName("should throw exception when not played song")
	void play_song_should_throw_exception() {
		//given
		long songId = 1L;
		SongDto songDto = new SongDto(songId, "Song name", 3L, SongLanguage.ENGLISH);
		when(songifyCrudFacade.findSongDtoById(anyLong())).thenReturn(songDto);
		when(songPlayer.playByName(songDto.name())).thenReturn("Fail");
		//when
		Throwable throwable = catchThrowable(() -> songPlayerFacade.playSong(songId));
		//then
		assertThat(throwable).isInstanceOf(RuntimeException.class);
		assertThat(throwable.getMessage()).isEqualTo("Song is not playing");
	}
}