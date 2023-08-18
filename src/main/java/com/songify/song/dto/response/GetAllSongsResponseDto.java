package com.songify.song.dto.response;

import com.songify.song.dto.SongEntity;

import java.util.*;

public record GetAllSongsResponseDto(Map<Integer, SongEntity> songs) {
}