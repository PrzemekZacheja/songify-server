package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;

class AlbumMapper {


    static AlbumDto mapAlbumToAlbumDto(final Album album) {
        return AlbumDto.builder()
                       .id(album.getId())
                       .title(album.getTitle())
                       .build();
    }
}