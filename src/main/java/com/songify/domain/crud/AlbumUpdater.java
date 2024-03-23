package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AlbumUpdater {

    AlbumProvider albumProvider;
    AlbumRepository albumRepository;

    AlbumDto updateNameById(final Long id, final String newAlbumName) {
        Album album = albumRepository.updateTitleById(newAlbumName, id);
        return AlbumMapper.mapAlbumToAlbumDto(album);
    }
}