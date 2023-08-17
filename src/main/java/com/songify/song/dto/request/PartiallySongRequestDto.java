package com.songify.song.dto.request;

import jakarta.validation.constraints.NotNull;

public record PartiallySongRequestDto(@NotNull(message = "Song name cannot be null")
                                      String songName,
                                      @NotNull(message = "Artist name cannot be null")
                                      String artistName) {
}