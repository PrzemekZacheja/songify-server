package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistAdder {

    private ArtistRepository artistRepository;

    ArtistDto addArtist(final ArtistRequestDto artistRequestDto) {
        Artist artist = new Artist(artistRequestDto.name());
        Artist saved = artistRepository.save(artist);
        return new ArtistDto(saved.getId(), saved.getName());
    }
}