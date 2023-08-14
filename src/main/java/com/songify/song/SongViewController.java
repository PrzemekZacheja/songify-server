package com.songify.song;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class SongViewController {

    Map<Integer, SongDto> databaseInMemory = new HashMap<>();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/views/songs")
    public String songs(Model model) {
        databaseInMemory.put(1, new SongDto("Song1"));
        databaseInMemory.put(2, new SongDto("Song2"));
        databaseInMemory.put(3, new SongDto("Song3"));

        model.addAttribute("songs", databaseInMemory);
        return "songs";
    }

}