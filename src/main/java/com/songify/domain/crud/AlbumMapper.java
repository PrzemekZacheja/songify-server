package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;

import java.util.Set;
import java.util.stream.Collectors;

class AlbumMapper {

    static Set<AlbumDto> mapAlbumsToAlbumDtos(final Set<Album> albumSet) {
        return albumSet.stream()
                       .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                       .collect(Collectors.toSet());
    }


    static AlbumDto mapAlbumToAlbumDto(final Album album) {
        return AlbumDto.builder()
                       .id(album.getId())
                       .title(album.getTitle())
                       .build();
    }
}