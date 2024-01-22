package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDomainDto;
import com.songify.infrastructure.crud.song.controller.SongControllerMapper;
import com.songify.infrastructure.crud.song.controller.dto.request.PartiallyUpdateSongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
public class SongifyCrudFacade {

    private final SongAdder songAdder;
    private final SongProvider songProvider;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;
    private final ArtistAdder artistAdder;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;

    public ArtistDto addArtist(ArtistRequestDto artistRequestDto) {
        return artistAdder.addArtist(artistRequestDto);
    }

    public GenreDto addGenre(GenreRequestDto genreRequestDto) {
        return genreAdder.addGenre(genreRequestDto);
    }

    public AlbumDto addAlbumWithSongs(AlbumRequestDto albumRequestDto) {
        return albumAdder.addAlbumWithSong(albumRequestDto);
    }

    public List<SongDomainDto> findAll(final Pageable pageable) {
        return songProvider.findAll(pageable)
                           .stream()
                           .map(SongDomainMapper::mapFromSongToSongDomainDto)
                           .toList();
    }

    public SongDomainDto getById(final long id) {
        Song byId = songProvider.getById(id);
        return SongDomainMapper.mapFromSongToSongDomainDto(byId);
    }

    public SongDomainDto addSong(final SongDomainDto songDomainDto) {
        Song song = SongDomainMapper.mapFromSongDomainDtoToSong(songDomainDto);
        return SongDomainMapper.mapFromSongToSongDomainDto(songAdder.addSong(song));
    }

    public void deleteById(final Long id) {
        songDeleter.deleteById(id);
    }

    public void updateById(final Long id, final SongDomainDto songToPut) {
        Song song = SongDomainMapper.mapFromSongDomainDtoToSong(songToPut);
        songUpdater.updateById(id, song);
    }

    public SongDomainDto partiallyUpdateById(final Long id,
                                             final PartiallyUpdateSongRequestDto partiallySongRequestDto) {
        SongDomainDto songDomainDto = SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSong(
                partiallySongRequestDto,
                id);
        return songUpdater.partiallyUpdateById(id, songDomainDto);
    }
}