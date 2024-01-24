package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/artists")
class ArtistController {

    private final SongifyCrudFacade songifyCrudFacade;

    @PostMapping
    ResponseEntity<ArtistDto> addArtist(@RequestBody ArtistRequestDto artistRequestDto) {
        return ResponseEntity.ok(songifyCrudFacade.addArtist(artistRequestDto));
    }

    @GetMapping
    ResponseEntity<AllArtistsDto> findAllArtists(Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(pageable);
        AllArtistsDto allArtistsDto = AllArtistsDto.builder()
                                                   .artists(allArtists)
                                                   .build();
        return ResponseEntity.ok(allArtistsDto);
    }
}