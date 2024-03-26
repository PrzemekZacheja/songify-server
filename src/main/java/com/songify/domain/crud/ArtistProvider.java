package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistWithAlbumsDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class ArtistProvider {

    private final ArtistRepository repository;
    private final AlbumProvider albumProvider;

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

    Artist findById(final Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ArtistNotFoundException(id));

    }

    Set<ArtistWithAlbumsDto> findAllArtistsWithAlbumsDto(final Pageable pageable) {
        Set<ArtistDto> allArtistsDto = findAllArtists(pageable);
        return allArtistsDto.stream()
                            .map(artistDto -> ArtistWithAlbumsDto.builder()
                                                                 .id(artistDto.id())
                                                                 .name(artistDto.name())
                                                                 .albums(albumProvider.findAlbumsDtoByArtistId(artistDto.id()))
                                                                 .build())
                            .collect(Collectors.toSet());
    }
}