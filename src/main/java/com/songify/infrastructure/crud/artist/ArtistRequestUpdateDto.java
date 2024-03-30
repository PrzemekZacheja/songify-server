package com.songify.infrastructure.crud.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

record ArtistRequestUpdateDto(
        @NotNull(message = "Name is required")
        @NotEmpty(message = "Name is required")
        String name
) {

}