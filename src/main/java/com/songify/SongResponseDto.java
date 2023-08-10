package com.songify;

import java.util.*;

public record SongResponseDto(Map<Integer, SongDto> songs) {
}