package com.songify.domain.crud;

public class SongifyCrudFacadeConfig {

    public static SongifyCrudFacade createSongifyCrudFacade(final SongRepository songRepository,
                                                            final ArtistRepository artistRepository,
                                                            final GenreRepository genreRepository,
                                                            final AlbumRepository albumRepository) {
        SongAdder songAdder = new SongAdder(songRepository);
        SongProvider songProvider = new SongProvider(songRepository);
        SongDeleter songDeleter = new SongDeleter(songRepository);
        SongUpdater songUpdater = new SongUpdater(songRepository, songProvider);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumAdder albumAdder = new AlbumAdder(albumRepository, songProvider);
        ArtistProvider artistProvider = new ArtistProvider(artistRepository);
        AlbumProvider albumPovider = new AlbumProvider(albumRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistProvider, albumPovider, songDeleter, albumDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistProvider, albumPovider);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistProvider);
        GenreProvider genreProvider = new GenreProvider(genreRepository);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository);
        return new SongifyCrudFacade(songAdder,
                                     songProvider,
                                     songDeleter,
                                     songUpdater,
                                     artistAdder,
                                     genreAdder,
                                     albumAdder,
                                     artistProvider,
                                     albumPovider,
                                     artistDeleter,
                                     artistAssigner,
                                     artistUpdater,
                                     genreProvider,
                                     genreDeleter);
    }
}