package com.songify.domain.crud;

public class SongifyCrudFacadeConfig {

    public static SongifyCrudFacade createSongifyCrudFacade(final SongRepository songRepository,
                                                            final ArtistRepository artistRepository,
                                                            final GenreRepository genreRepository,
                                                            final AlbumRepository albumRepository) {
        SongProvider songProvider = new SongProvider(songRepository);
        SongDeleter songDeleter = new SongDeleter(songRepository);
        SongUpdater songUpdater = new SongUpdater(songRepository, songProvider);
        AlbumAdder albumAdder = new AlbumAdder(albumRepository, songProvider);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumProvider albumProvider = new AlbumProvider(albumRepository);
        ArtistProvider artistProvider = new ArtistProvider(artistRepository, albumProvider);
        SongAdder songAdder = new SongAdder(songRepository, albumProvider, songProvider);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository, albumAdder, songAdder);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository, albumProvider, songDeleter);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistProvider, albumProvider, songDeleter, albumDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistProvider, albumProvider, artistRepository);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistProvider);
        GenreProvider genreProvider = new GenreProvider(genreRepository, songProvider);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository, songProvider, songDeleter);
        GenreAssigner genreAssigner = new GenreAssigner(songProvider, genreProvider);
        GenreUpdater genreUpdater = new GenreUpdater(genreRepository, genreProvider);
        AlbumUpdater albumUpdater = new AlbumUpdater(albumProvider, albumRepository);
        AlbumAssigner albumAssigner = new AlbumAssigner(albumProvider, artistProvider);
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
                                     albumUpdater,
                                     genreAdder,
                                     genreProvider,
                                     genreDeleter,
                                     genreAssigner,
                                     genreUpdater,
                                     albumAssigner
        );
    }
}