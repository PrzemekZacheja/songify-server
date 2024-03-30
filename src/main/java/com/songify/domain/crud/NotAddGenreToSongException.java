package com.songify.domain.crud;

class NotAddGenreToSongException extends RuntimeException {
	NotAddGenreToSongException(final String message) {
		super(message);
	}
}