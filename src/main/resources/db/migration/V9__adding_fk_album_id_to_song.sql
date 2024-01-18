ALTER TABLE songs
    ADD album_id BIGINT REFERENCES album (id);