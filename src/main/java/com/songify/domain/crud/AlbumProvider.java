package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class AlbumProvider {

    private final AlbumRepository repository;

    AlbumInfo findAlbumByIdWithArtistsAndSongs(final long id) {
        log.info("Album with artists and songs by id " + id + " was found");
        return repository.findByIdAndSongs_IdAndArtists_Id(id)
                         .orElseThrow(() -> new AlbumNotFoundException("Album with id " + id + " wasn't " + "found"));

    }

    Set<Album> findAlbumsByArtistId(final Long id) {
        return repository.findAllAlbumsByArtistsId(id);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long id) {
        log.info("Albums with artists by id " + id + " was found");
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
        log.info("All albums were found");
        return allAlbums.stream()
                        .map(album -> new AlbumDto(album.getId(), album.getTitle()))
                        .collect(Collectors.toSet());
    }
}