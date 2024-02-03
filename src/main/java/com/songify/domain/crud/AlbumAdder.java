package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AlbumAdder {

    private final AlbumRepository albumRepository;
    private final SongProvider songProvider;

    AlbumDto addAlbumWithSong(final AlbumRequestDto albumRequestDto) {
        Song songById = songProvider.findSongById(albumRequestDto.songId());
        Album album = new Album(albumRequestDto.title(), albumRequestDto.releaseDate());
        album.addSong(songById);
        albumRepository.save(album);
        return new AlbumDto(album.getId(), album.getTitle());
    }

}