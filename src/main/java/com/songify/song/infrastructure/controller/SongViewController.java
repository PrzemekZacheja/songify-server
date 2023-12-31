package com.songify.song.infrastructure.controller;

import com.songify.song.domain.model.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    final Map<Integer, Song> databaseInMemory = new HashMap<>();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/views/songs")
    public String songs(Model model) {
        databaseInMemory.put(1, new Song("Song1", "Artist1"));
        databaseInMemory.put(2, new Song("Song2", "Artist2"));
        databaseInMemory.put(3, new Song("Song3", "Artist3"));

        model.addAttribute("songs", databaseInMemory);
        return "songs";
    }

}