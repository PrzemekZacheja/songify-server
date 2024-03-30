package com.songify.domain.songplayer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class SongPlayer {

	private final YouTubeHttpClient httpClient;

	public String playByName(String songName) {
		httpClient.play(songName);
		return "Success";
	}
}