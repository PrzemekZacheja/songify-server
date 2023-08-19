package com.songify.song.infrastructure.controller;

import com.songify.song.domain.model.SongEntity;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class SongViewController {

    Map<Integer, SongEntity> databaseInMemory = new HashMap<>();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/views/songs")
    public String songs(Model model) {
        databaseInMemory.put(1, new SongEntity("Song1", "Artist1"));
        databaseInMemory.put(2, new SongEntity("Song2", "Artist2"));
        databaseInMemory.put(3, new SongEntity("Song3",  "Artist3"));

        model.addAttribute("songs", databaseInMemory);
        return "songs";
    }

}