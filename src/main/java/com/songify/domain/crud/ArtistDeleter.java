package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class ArtistDeleter {

    private final ArtistRepository artistRepository;
    private final ArtistProvider artistProvider;
    private final AlbumProvider albumProvider;
    private final SongDeleter songDeleter;
    private final AlbumDeleter albumDeleter;

    void deleteArtistByIdWithSongsAndAlbums(final Long id) {
        Artist artist = artistProvider.findById(id);
        Set<Album> albumsByArtistId = albumProvider.findAlbumsByArtistId(artist.getId());
        if (albumsByArtistId.isEmpty()) {
            log.warn("Artist with id {} has no albums, so it can be deleted", id);
            artistRepository.deleteById(id);
            return;
        }
        deleteArtistAndSongsWhenHaveOnlyOneAlbumByArtistId(id, albumsByArtistId);
        albumsByArtistId.stream()
                        .filter(album -> album.getArtists()
                                              .size() >= 2)
                        .forEach(album -> album.deleteArtist(artist));
    }

    private void deleteArtistAndSongsWhenHaveOnlyOneAlbumByArtistId(final Long id, final Set<Album> albumsByArtistId) {
        Set<Album> albumsWithOnlyOneArtist = albumsByArtistId.stream().
                                                             filter(album -> album.getArtists()
                                                                                  .size() == 1)
                                                             .collect(Collectors.toSet());
        Set<Long> allSongsIdsToDelete = albumsByArtistId
                .stream()
                .flatMap(album -> album.getSongs()
                                       .stream())
                .map(Song::getId)
                .collect(Collectors.toSet());
        songDeleter.deleteAllSongsById(allSongsIdsToDelete);
        Set<Long> idsAlbumsToDelete = albumsWithOnlyOneArtist.stream()
                                                             .map(Album::getId)
                                                             .collect(Collectors.toSet());
        albumDeleter.deleteAllAlbumsById(idsAlbumsToDelete);
        artistRepository.deleteById(id);
    }
}