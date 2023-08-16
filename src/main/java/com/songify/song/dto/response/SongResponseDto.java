package com.songify.song.dto.response;

import com.songify.song.dto.SongDto;

import java.util.*;

public record SongResponseDto(Map<Integer, SongDto> songs) {
}