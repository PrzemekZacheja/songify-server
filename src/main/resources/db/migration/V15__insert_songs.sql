INSERT INTO public.songs (name, release_date, duration, language, genre_id, album_id)
SELECT 'Song ' || i, NOW() - (i || ' days')::INTERVAL, (i * 1000), 'ENGLISH', (i % 15) + 1, (i % 15) + 1
FROM generate_series(1, 15) AS i;