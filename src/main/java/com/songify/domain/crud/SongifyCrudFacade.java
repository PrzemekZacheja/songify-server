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
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final ArtistProvider artistProvider;
    private final AlbumProvider albumProvider;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;

    public ArtistDto addArtist(ArtistRequestDto artistRequestDto) {
        try {
            ArtistDto artistDto = artistAdder.addArtist(artistRequestDto);
            return artistDto;
        } catch (IllegalArgumentException e) {
            log.error("Error while adding artist: {}, name must not be empty", e.getMessage());
        }
        return null;
    }

    public GenreDto addGenre(GenreRequestDto genreRequestDto) {
        return genreAdder.addGenre(genreRequestDto);
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

    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistProvider.findAllArtists(pageable);
    }

    public List<SongDto> findAllSongs(final Pageable pageable) {
        return songProvider.findAll(pageable);
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
}