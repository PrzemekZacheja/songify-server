package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AlbumUpdater {

    AlbumProvider albumProvider;
    AlbumRepository albumRepository;

    void updateNameById(final Long id, final String newAlbumName) {
        Album album = albumRepository.updateTitleById(newAlbumName, id);
        AlbumMapper.mapAlbumToAlbumDto(album);
    }
}