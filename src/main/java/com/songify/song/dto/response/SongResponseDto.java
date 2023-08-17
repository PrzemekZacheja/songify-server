package com.songify.song.dto.response;

import com.songify.song.dto.SongEntity;

import java.util.*;

public record SongResponseDto(Map<Integer, SongEntity> songs) {
}