package com.songify.song.domain.repository;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends org.springframework.data.repository.Repository<Song, Long> {

    public Song save(Song song);

    public List<Song> findAll();

    public Song getById(Integer id);

    public Song remove(Integer id);

    public void put(Integer id, Song songToPut);

    public List<Song> getLimitedSongs(Integer limitOfSongs);
}