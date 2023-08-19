package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.domain.model.SongEntity;

import java.util.*;

public record GetAllSongsResponseDto(Map<Integer, SongEntity> songs) {
}