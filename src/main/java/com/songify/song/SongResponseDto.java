package com.songify.song;

import java.util.*;

public record SongResponseDto(Map<Integer, SongDto> songs) {
}