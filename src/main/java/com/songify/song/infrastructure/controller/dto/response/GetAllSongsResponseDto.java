package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.domain.model.Song;

import java.util.*;

public record GetAllSongsResponseDto(List<Song> songs) {
}