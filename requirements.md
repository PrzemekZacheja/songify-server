
Aplikacja SONGIFY to aplikacja CRUD, która umożliwia użytkownikom zarządzanie albumami, artystami i piosenkami. Poniżej przedstawiam szczegółowe wymagania w podpunktach:

1. **Zarządzanie albumami**
    * Dodawanie nowych albumów do bazy danych, wraz z piosenką zawartą w środku-relacja między encjami.
    * Wyświetlanie listy albumów z bazy danych.
    * Edycja istniejących albumów.
    * Usuwanie albumów z bazy danych.

2. **Zarządzanie artystami**
    * Dodawanie nowych artystów do bazy danych.
    * Wyświetlanie listy artystów z bazy danych.
    * Edycja istniejących artystów.
    * Usuwanie artystów z bazy danych.

3. **Zarządzanie piosenkami**
    * Dodawanie nowych piosenek do bazy danych, wraz z artystą-relacja między encjami.
    * Wyświetlanie listy piosenek z bazy danych.
    * Edycja istniejących piosenek.
    * Usuwanie piosenek z bazy danych.

HAPPY-PATH:
* user tworzy album EMINEM z piosenkami "Til I collapse" i "Love Yourself" o gatunku RAP 
W aplikacji nie ma zapisanych żadnych piosenek, albumów czy gatunków

1. when I use /songs there are no songs displayed
2. when I use POST /song with the song "Til I collapse" the song with id:1 and status 200 OK is returned
3. when I use POST /song with the song "Love Yourself", the song with id:2 and status 200 OK are returned
4. when I use /genre no genres are displayed
5. when I use POST /genre with "RAP" then the genre "RAP" with id 1 is returned
6. when I use /song/1 I see a song with the genre "default"
7. when I use PUT /song/1/genre/1 the genre "RAP" is added to the song with id 1
8. when I use /song/1 I see a song with the genre "RAP"
9. when I use PUT /song/2/genre/1 the genre with id 1 is added to the song with id 2
10. when I use GET /album, no albums are displayed
11. when I use POST /album with the album "EMINEM ALBUM", the album with id 1 is returned
12. when I use GET /album/1 there are no songs added
13. when I use PUT /album/1/song/1 the song with id 1 is added to the album with id 1
14. when I use PUT /album/1/song/2 the song with id 2 is added to the album with id 1
15. when I use GET /album/1 I see 2 songs with id 1 and id 2
16. when I use POST /artist with the artist "EMINE", the artist with id 1 is added to the album
17. when I use PUT /album/1/artist/1 the artist with id 1 is added to the album with id 1