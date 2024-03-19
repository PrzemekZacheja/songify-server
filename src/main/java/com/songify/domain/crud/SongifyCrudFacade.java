package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import com.songify.infrastructure.crud.song.controller.SongControllerMapper;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
@Log4j2
public class SongifyCrudFacade {

    private final SongAdder songAdder;
    private final SongProvider songProvider;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final ArtistAdder artistAdder;
    private final ArtistProvider artistProvider;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;
    private final AlbumAdder albumAdder;
    private final AlbumDeleter albumDeleter;
    private final AlbumProvider albumProvider;
    private final GenreAdder genreAdder;
    private final GenreProvider genreProvider;
    private final GenreDeleter genreDeleter;
    private final GenreAssigner genreAssigner;

    public ArtistDto addArtist(ArtistRequestDto artistRequestDto) {
        try {
            return artistAdder.addArtist(artistRequestDto);
        } catch (IllegalArgumentException e) {
            log.error("Error while adding artist: {}, name must not be empty", e.getMessage());
        }
        return null;
    }

    public GenreDto addGenre(GenreRequestDto genreRequestDto) {
        try {
            return genreAdder.addGenre(genreRequestDto);
        } catch (IllegalArgumentException e) {
            log.error("Error while adding genre: {}, name must not be empty", e.getMessage());
        }
        return null;
    }

    public SongDto addSong(final SongRequestDto songRequestDto) {
        Song song = SongMapper.mapFromSongRequestDtoToSong(songRequestDto);
        Song added = songAdder.addSong(song);
        log.info("Song added with id: {}", added.getId());
        return SongMapper.mapFromSongToSongDto(added);
    }

    public AlbumDto addAlbumWithSongs(AlbumRequestDto albumRequestDto) {
        return albumAdder.addAlbumWithSong(albumRequestDto);
    }

    public void addArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }

    public ArtistDto addArtistWithDefaultAlbumAndSongs(ArtistRequestDto artistRequestDto) {
        return artistAdder.addArtistWithDefaultAlbumAndSongs(artistRequestDto);
    }

    public Set<ArtistDto> findAllArtistsDto(Pageable pageable) {
        return artistProvider.findAllArtists(pageable);
    }

    public List<SongDto> findAllSongsDto(final Pageable pageable) {
        return songProvider.findAll(pageable);
    }

    Set<GenreDto> findAllGenres(final Pageable pageable) {
        return genreProvider.findAll(pageable);
    }

    public SongDto findSongById(final long id) {
        return songProvider.findSongDtoById(id);
    }

    public AlbumInfo findAlbumByIdWithArtistsAndSongs(final long id) {
        return albumProvider.findAlbumByIdWithArtistsAndSongs(id);
    }

    public void deleteArtistByIdWithSongsAndAlbums(final Long id) {
        artistDeleter.deleteArtistByIdWithSongsAndAlbums(id);
    }

    public void deleteSongById(final Long id) {
        songDeleter.deleteById(id);
        log.info("Song with id: {} deleted", id);
    }

    public SongDto updateSongById(final Long id, final SongRequestDto songToPut) {
        Song song = SongMapper.mapFromSongRequestToSong(songToPut);
        return songUpdater.updateById(id, song);
    }

    public ArtistDto updateArtistNameById(final Long id, final String name) {
        return artistUpdater.updateNameById(id, name);
    }

    public SongDto partiallyUpdateSongById(final Long id,
                                           final PartiallyUpdateSongRequestDto partiallySongRequestDto) {
        SongDto songDto = SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSong(
                partiallySongRequestDto,
                id);
        return songUpdater.partiallyUpdateById(id, songDto);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long id) {
        return AlbumMapper.mapAlbumsToAlbumDtos(
                albumProvider.findAlbumsByArtistId(id));
    }

    void deleteGenreById(final Long id) {
        genreDeleter.deleteById(id);
    }

    Set<AlbumDto> findAllAlbumsDto(Pageable pageable) {
        return albumProvider.findAllAlbums(pageable);
    }

    void addGenreToSong(final Long genreId, final Long songId) {
        genreAssigner.addGenreToSong(genreId, songId);
    }

    void deleteAlbumById(final Long id) {
        try {
            albumDeleter.deleteById(id);
        } catch (AlbumDeleterException e) {
            log.error("Album with id: {} cannot be deleted because it contains songs", id);
        }
    }

    void deleteAlbumWithSongsById(final Long id) {
        albumDeleter.deleteAlbumWithSongsById(id);
    }
}