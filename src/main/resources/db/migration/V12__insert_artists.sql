INSERT INTO artist (name)
SELECT 'Artist ' || i
FROM generate_series(1, 15) AS i;