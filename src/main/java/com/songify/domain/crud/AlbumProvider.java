package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumProvider {

    private final AlbumRepository repository;

    AlbumInfo findAlbumByIdWithArtistsAndSongs(final long id) {
        return repository.findByIdAndSongs_IdAndArtists_Id(id)
                         .orElseThrow(() -> new AlbumNotFoundException("Album with id " + id + " wasn't " + "found"));

    }

    Set<Album> findAlbumsByArtistId(final Long id) {
        return repository.findAllAlbumsByArtistsId(id);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long id) {
        return repository.findAllAlbumsByArtistsId(id)
                         .stream()
                         .map(AlbumMapper::mapAlbumToAlbumDto)
                         .collect(Collectors.toSet());
    }

    Album findById(final Long albumId) {
        return repository.findById(albumId)
                         .orElseThrow(() -> new AlbumNotFoundException("Album with id " + albumId + " wasn't found"));
    }

    Set<AlbumDto> findAllAlbums(Pageable pageable) {
        Set<Album> allAlbums = repository.findAllAlbums(pageable);
        return allAlbums.stream()
                        .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                        .collect(Collectors.toSet());
    }
}