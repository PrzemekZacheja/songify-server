package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AlbumProvider {

    private final AlbumRepository repository;

    AlbumInfo findAlbumByIdWithArtistsAndSongs(final long id) {
        return repository.findByIdAndSongs_IdAndArtists_Id(id)
                         .orElseThrow(() -> new AlbumNotFoundException("Album with id " + id + " wasn't " + "found"));

    }
}