package com.songify.song.dto;

import org.springframework.http.HttpStatus;

public record ErrorSongResponseDto(String message, HttpStatus  status) {
}