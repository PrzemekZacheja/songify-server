ALTER TABLE album
    ADD version BIGINT DEFAULT 0;

ALTER TABLE artist
    ADD version BIGINT DEFAULT 0;

ALTER TABLE genre
    ADD version BIGINT DEFAULT 0;

ALTER TABLE songs
    ADD version BIGINT DEFAULT 0;