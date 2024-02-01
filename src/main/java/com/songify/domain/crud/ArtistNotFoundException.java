package com.songify.domain.crud;

class ArtistNotFoundException extends RuntimeException {


    ArtistNotFoundException(final Long id) {
        super("artist with " + id + " not found");
    }

}