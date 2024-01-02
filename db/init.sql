-- CREATE TABLE if not exists songs
-- (
--     id     BIGSERIAL PRIMARY KEY,
--     artist VARCHAR(255) NOT NULL,
--     name   VARCHAR(255) NOT NULL
-- );

CREATE TABLE if not exists songs
(
    id     BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    artist       VARCHAR(255) NOT NULL,
    release_date TIMESTAMP WITHOUT TIME ZONE,
    duration     BIGINT,
    language     VARCHAR(255)
);


INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Ariana Grande', 'Tik Tok');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Shawn Mendes', 'Bad Guy');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Taylor Swift', 'Shape of You');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Ed Sheeran', 'Love Story');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Billie Eilish', 'Thank U, Next');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Adele', 'Believer');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Post Malone', 'Senorita');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Drake', 'Havana');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Justin Bieber', 'Uptown Funk');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Dua Lipa', 'All About That Bass');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Bruno Mars', 'Rolling in the Deep');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Rihanna', 'Cant Stop the Feeling');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Imagine Dragons', 'Blank Space');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'The Weeknd', 'Shake It Off');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Katy Perry', 'Sorry');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Coldplay', 'Happier');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Beyonce', 'Chandelier');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Maroon 5', 'Royals');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Cardi B', 'Wrecking Ball');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Lizzo', 'Thinking Out Loud');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Sam Smith', 'Hotline Bling');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Taylor Swift', 'Love Yourself');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Ed Sheeran', 'Watermelon Sugar');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Post Malone', 'Dance Monkey');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Ariana Grande', 'Stressed Out');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Shawn Mendes', 'Someone Like You');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Billie Eilish', 'Blinding Lights');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Dua Lipa', 'Cant Feel My Face');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Bruno Mars', 'WAP');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Rihanna', 'Firework');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Imagine Dragons', 'Just the Way You Are');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Katy Perry', 'Grenade');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Beyonce', 'This Love');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Maroon 5', 'Poker Face');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Cardi B', 'Shape of You');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Lizzo', 'Call Me Maybe');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Sam Smith', 'We Found Love');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Taylor Swift', 'Locked Out of Heaven');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Ed Sheeran', 'Love Me Like You Do');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Post Malone', 'Radioactive');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Ariana Grande', 'Titanium');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Shawn Mendes', 'Stay');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Billie Eilish', 'Happy');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Dua Lipa', 'Royals');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Bruno Mars', 'Blank Space');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Rihanna', 'All of Me');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Imagine Dragons', 'Counting Stars');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Katy Perry', 'Take Me to Church');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Beyonce', 'The Hills');
INSERT INTO songs (id, artist, name)
VALUES (nextval('songs_id_seq'), 'Maroon 5', 'Despacito');