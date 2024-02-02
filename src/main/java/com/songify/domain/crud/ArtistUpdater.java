package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
class ArtistUpdater {

    private final ArtistProvider artistProvider;

    ArtistDto updateNameById(final Long id, final String name) {
        Artist artist = artistProvider.findById(id);
        artist.setName(name);
        log.info("Artist with id: {} updated name to: {}", id, name);
        return ArtistDto.builder()
                        .id(id)
                        .name(name)
                        .build();
    }
}