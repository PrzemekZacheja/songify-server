package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumInfo;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.ArtistWithAlbumsDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.GenreWithSongsDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
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
    private final AlbumUpdater albumUpdater;
    private final GenreAdder genreAdder;
    private final GenreProvider genreProvider;
    private final GenreDeleter genreDeleter;
    private final GenreAssigner genreAssigner;
    private final GenreUpdater genreUpdater;
    private final AlbumAssigner albumAssigner;

    public ArtistDto addArtist(ArtistRequestDto artistRequestDto) {
        try {
            return artistAdder.addArtist(artistRequestDto);
        } catch (IllegalArgumentException e) {
            log.error("Error while adding artist: {}, name must not be empty", e.getMessage());
        }
        return null;
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

    public AlbumInfo findAlbumByIdWithArtistsAndSongs(final long id) {
        return albumProvider.findAlbumByIdWithArtistsAndSongs(id);
    }

    public void deleteArtistByIdWithSongsAndAlbums(final Long id) {
        artistDeleter.deleteArtistByIdWithSongsAndAlbums(id);
    }

    public ArtistDto updateArtistNameById(final Long id, final String name) {
        return artistUpdater.updateNameById(id, name);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long id) {
        return albumProvider.findAlbumsDtoByArtistId(id);
    }

    public GenreDto addGenre(GenreRequestDto genreRequestDto) {
        try {
            return genreAdder.addGenre(genreRequestDto);
        } catch (IllegalArgumentException e) {
            log.error("Error while adding genre: {}, name must not be empty", e.getMessage());
        }
        return null;
    }

    Set<GenreDto> findAllGenres(final Pageable pageable) {
        return genreProvider.findAll(pageable);
    }

    void deleteGenreById(final Long id) {
        genreDeleter.deleteById(id);
    }

    void addGenreToSong(final Long genreId, final Long songId) {
        genreAssigner.addGenreToSong(genreId, songId);
    }

    GenreDto updateGenreNameById(final Long id, final String newNameOfGenre) {
        return genreUpdater.updateNameOfGenreById(id, newNameOfGenre);
    }

    GenreDto findGenreBySongId(final Long id) {
        return genreProvider.findGenreBySongId(id);
    }

    public SongDto addSong(final SongRequestDto songRequestDto) {
        SongDto added = songAdder.addSong(songRequestDto);
        log.info("Song added with id: {}", added.id());
        return added;
    }

    public AlbumDto addAlbumWithSongs(AlbumRequestDto albumRequestDto) {
        return albumAdder.addAlbumWithSong(albumRequestDto);
    }

    public List<SongDto> findAllSongsDto(final Pageable pageable) {
        return songProvider.findAll(pageable);
    }

    public SongDto findSongById(final long id) {
        return songProvider.findSongDtoById(id);
    }

    public void deleteSongById(final Long id) {
        songDeleter.deleteById(id);
    }

    public SongDto updateSongById(final Long id, final SongRequestDto songToPut) {
        return songUpdater.updateById(id, songToPut);
    }

    void addSongToAlbum(final Long idSong, final Long idAlbum) {
        songAdder.addSongToAlbum(idSong, idAlbum);
    }

    SongDto updateSongNameById(final Long id, final String songNameEdit) {
        return songUpdater.updateNameById(id, songNameEdit);
    }

    SongDto updateSongDurationById(final Long id, final long duration) {
        return songUpdater.updateDurationById(id, duration);
    }

    Set<AlbumDto> findAllAlbumsDto(Pageable pageable) {
        return albumProvider.findAllAlbums(pageable);
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

    void updateAlbumNameById(final Long id, final String newAlbumName) {
        albumUpdater.updateNameById(id, newAlbumName);
    }

    void addAlbumToArtist(final Long albumId, final Long artistId) {
        albumAssigner.addAlbumToArtist(albumId, artistId);
    }


    GenreWithSongsDto findGenreWithSongs(final Long id) {
        return genreProvider.findGenreWithSongs(id);
    }

    Set<ArtistWithAlbumsDto> findAllArtistsDtoWithAlbumsDto(final Pageable pageable) {
        return artistProvider.findAllArtistsWithAlbumsDto(pageable);
    }
}