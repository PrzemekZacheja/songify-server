package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
class AlbumAdder {

    private final AlbumRepository albumRepository;
    private final SongProvider songProvider;

    AlbumDto addAlbumWithSong(final AlbumRequestDto albumRequestDto) {
        Song songById = songProvider.findSongById(albumRequestDto.songId());
        Album album = new Album(albumRequestDto.title(), albumRequestDto.releaseDate());
        album.addSong(songById);
        albumRepository.save(album);
        log.info("Album added: {}", album.toString());
        return new AlbumDto(album.getId(), album.getTitle());
    }

}