package com.songify.domain.crud;

import org.jetbrains.annotations.NotNull;

class SongDomainMapper {

    @NotNull
    public static SongDomainDto mapFromSongToSongDomainDto(Song song) {
        return SongDomainDto.builder()
                            .artist(song.getArtist())
                            .duration(song.getDuration())
                            .language(song.getLanguage())
                            .name(song.getName())
                            .id(song.getId())
                            .build();
    }

    static Song mapFromSongDomainDtoToSong(final SongDomainDto songDomainDto) {
        return Song.builder()
                   .artist(songDomainDto.artist())
                   .duration(songDomainDto.duration())
                   .language(songDomainDto.language())
                   .name(songDomainDto.name())
                   .id(songDomainDto.id())
                   .build();
    }

}