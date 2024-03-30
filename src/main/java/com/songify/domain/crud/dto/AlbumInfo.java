package com.songify.domain.crud.dto;

import com.songify.domain.crud.Album;
import com.songify.domain.crud.Artist;
import com.songify.domain.crud.Genre;
import com.songify.domain.crud.Song;
import com.songify.domain.crud.SongLanguage;

import java.time.Instant;
import java.util.Set;

/**
 * Projection for {@link Album}
 */
public interface AlbumInfo {
    Long getId();

    String getTitle();

    Instant getReleaseDate();

    Set<SongInfo> getSongs();

    Set<ArtistInfo> getArtists();

    /**
     * Projection for {@link Song}
     */
    interface SongInfo {
        Long getId();

        String getName();

        Instant getReleaseDate();

        Long getDuration();

        SongLanguage getLanguage();

        GenreInfo getGenre();

        /**
         * Projection for {@link Genre}
         */
        interface GenreInfo {
            Long getId();

            String getName();
        }
    }

    /**
     * Projection for {@link Artist}
     */
    interface ArtistInfo {
        Long getId();

        String getName();
    }
}