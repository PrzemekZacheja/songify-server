package com.songify.song;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SongRequestDto(@NotNull(message = "Song name cannot be null")
                             @NotEmpty(message = "Song name cannot be empty")
                             String songName) {
}