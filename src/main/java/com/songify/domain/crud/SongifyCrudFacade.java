package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
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

    public ArtistDto addArtist(ArtistRequestDto artistRequestDto) {
        return artistAdder.addArtist(artistRequestDto);
    }

    public GenreDto addGenre(GenreRequestDto genreRequestDto) {
        return genreAdder.addGenre(genreRequestDto);
    }

    public AlbumDto addAlbumWithSongs(AlbumRequestDto albumRequestDto) {
        return albumAdder.addAlbumWithSong(albumRequestDto);
    }

    public List<SongDto> findAll(final Pageable pageable) {
        return songProvider.findAll(pageable)
                           .stream()
                           .map(SongMapper::mapFromSongToSongDomainDto)
                           .toList();
    }

    public SongDto getById(final long id) {
        Song byId = songProvider.getById(id);
        return SongMapper.mapFromSongToSongDomainDto(byId);
    }

    public SongDto addSong(final SongRequestDto songRequestDto) {
        Song song = SongMapper.mapFromSongRequestDtoToSong(songRequestDto);
        Song added = songAdder.addSong(song);
        log.info("Song added with id: {}", added.getId());
        return SongMapper.mapFromSongToSongDomainDto(added);
    }

    public void deleteById(final Long id) {
        songDeleter.deleteById(id);
    }

    public SongDto updateById(final Long id, final SongRequestDto songToPut) {
        Song song = SongMapper.mapFromSongDomainDtoToSong(songToPut);
        return songUpdater.updateById(id, song);
    }

    public SongDto partiallyUpdateById(final Long id,
                                       final PartiallyUpdateSongRequestDto partiallySongRequestDto) {
        SongDto songDto = SongControllerMapper.mapFromPartiallyUpdateSongRequestDtoToSong(
                partiallySongRequestDto,
                id);
        return songUpdater.partiallyUpdateById(id, songDto);
    }
}