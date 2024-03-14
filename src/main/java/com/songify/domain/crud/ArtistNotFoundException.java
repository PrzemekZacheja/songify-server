package com.songify.domain.crud;

class ArtistNotFoundException extends RuntimeException {


    ArtistNotFoundException(final Long id) {
        super("Artist with id " + id + " not found");
    }

}