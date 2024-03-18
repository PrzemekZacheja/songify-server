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
        AlbumAdder albumAdder = new AlbumAdder(albumRepository, songProvider);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository, albumAdder, songAdder);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        ArtistProvider artistProvider = new ArtistProvider(artistRepository);
        AlbumProvider albumProvider = new AlbumProvider(albumRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository, albumProvider, songDeleter);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistProvider, albumProvider, songDeleter, albumDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistProvider, albumProvider);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistProvider);
        GenreProvider genreProvider = new GenreProvider(genreRepository);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository, songProvider, songDeleter);
        GenreAssigner genreAssigner = new GenreAssigner(songProvider, genreProvider);

        return new SongifyCrudFacade(songAdder,
                                     songProvider,
                                     songDeleter,
                                     songUpdater,
                                     artistAdder,
                                     artistProvider,
                                     artistDeleter,
                                     artistAssigner,
                                     artistUpdater,
                                     albumAdder,
                                     albumDeleter,
                                     albumProvider,
                                     genreAdder,
                                     genreProvider,
                                     genreDeleter,
                                     genreAssigner);
    }
}