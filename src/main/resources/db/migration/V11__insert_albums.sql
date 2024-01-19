INSERT INTO album (title, release_date)
SELECT 'Album ' || i, NOW() - (i || ' days')::INTERVAL
FROM generate_series(1, 15) AS i;