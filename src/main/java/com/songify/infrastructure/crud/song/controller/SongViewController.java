package com.songify.infrastructure.crud.song.controller;

import com.songify.domain.crud.SongLanguage;
import com.songify.domain.crud.dto.SongDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SongViewController {

    final Map<Integer, SongDto> databaseInMemory = new HashMap<>();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/views/songs")
    public String songs(Model model) {
        databaseInMemory.put(1, new SongDto(1L, "Song 1", "Artist 1", 10L, SongLanguage.ENGLISH));
        databaseInMemory.put(2, new SongDto(2L, "Song 2", "Artist 2", 20L, SongLanguage.ENGLISH));
        databaseInMemory.put(3, new SongDto(3L, "Song 3", "Artist 3", 30L, SongLanguage.ENGLISH));

        model.addAttribute("songs", databaseInMemory);
        return "songs";
    }

}