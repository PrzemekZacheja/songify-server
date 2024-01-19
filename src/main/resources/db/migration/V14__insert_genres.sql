INSERT INTO genre (name)
SELECT 'Genre ' || i
FROM generate_series(1, 15) AS i;