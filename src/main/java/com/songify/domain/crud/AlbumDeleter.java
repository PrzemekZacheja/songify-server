package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class AlbumDeleter {

    private final AlbumRepository albumRepository;
    private final AlbumProvider albumProvider;
    private final SongDeleter songDeleter;

    void deleteAllAlbumsById(final Set<Long> idsAlbumsToDelete) {
        albumRepository.deleteByIdIn(idsAlbumsToDelete);
    }

    void deleteById(final Long id) {
        if (albumProvider.findAlbumById(id)
                         .getSongs()
                         .isEmpty()) {
            albumRepository.deleteById(id);
            log.info("Album with id: " + id + " was deleted.");
        } else {
            throw new AlbumDeleterException("The album cannot be deleted because it contains songs.");
        }
    }

    void deleteAlbumWithSongsById(final Long id) {
        Album byId = albumProvider.findAlbumById(id);
        Set<Song> songs = byId.getSongs();
        Set<Long> songsIds = songs.stream()
                                  .map(Song::getId)
                                  .collect(Collectors.toSet());
        songDeleter.deleteAllSongsById(songsIds);
        albumRepository.deleteById(id);
    }
}