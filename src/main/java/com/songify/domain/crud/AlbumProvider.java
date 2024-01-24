package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class AlbumProvider {

    private final ArtistRepository repository;

    Set<ArtistDto> findAllArtists(final Pageable pageable) {
        return repository.findAll(pageable)
                         .stream()
                         .map(artist ->
                                      ArtistDto.builder()
                                               .id(artist.getId())
                                               .name(artist.getName())
                                               .build()
                             )
                         .collect(Collectors.toSet());

    }
}