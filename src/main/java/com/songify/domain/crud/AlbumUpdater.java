package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
class AlbumUpdater {

    AlbumProvider albumProvider;
    AlbumRepository albumRepository;

    void updateNameById(final Long id, final String newAlbumName) {
        Album album = albumRepository.updateTitleById(newAlbumName, id);
        AlbumMapper.mapAlbumToAlbumDto(album);
        log.info("Album updated: {}", album.toString());
    }
}