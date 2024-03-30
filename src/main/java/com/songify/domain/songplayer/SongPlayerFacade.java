package com.songify.domain.songplayer;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class SongPlayerFacade {

	private final SongifyCrudFacade songifyCrudFacade;
	private final SongPlayer songPlayer;

	public String playSong(Long songId) {
		SongDto songDtoById = songifyCrudFacade.findSongDtoById(songId);
		String name = songDtoById.name();
		String result = songPlayer.playByName(name);
		if (result.equals("Success")) {
			System.out.println("Song is playing");
			System.out.println("Song name: " + name);
			return "Success";
		}
		throw new RuntimeException("Song is not playing");
	}
}