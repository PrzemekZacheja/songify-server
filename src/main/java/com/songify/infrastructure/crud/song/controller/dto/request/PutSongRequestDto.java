package com.songify.infrastructure.crud.song.controller.dto.request;

import com.songify.domain.crud.song.SongLanguage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PutSongRequestDto(@NotNull(message = "Song name cannot be null")
                                @NotEmpty(message = "Song name cannot be empty")
                                String songName,
                                @NotNull(message = "Artist name cannot be null")
                                @NotEmpty(message = "Artist name cannot be empty")
                                String artistName,
                                Long duration,
                                SongLanguage language) {

}