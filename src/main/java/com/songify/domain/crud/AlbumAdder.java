package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
class AlbumAdder {

    private final AlbumRepository albumRepository;
    private final SongProvider songProvider;

    AlbumDto addAlbumWithSongs(final AlbumRequestDto albumRequestDto) {
        Set<Song> songs = songProvider.findSongsByIds(albumRequestDto.songsIds());
        Album album = new Album(albumRequestDto.title(), albumRequestDto.releaseDate());
        album.addSongs(songs);
        albumRepository.save(album);
        log.info("Album added: {}", album.toString());
        return new AlbumDto(album.getId(), album.getTitle());
    }

}