package com.songify.song.dto;

import java.util.*;

public record SongResponseDto(Map<Integer, SongDto> songs) {
}