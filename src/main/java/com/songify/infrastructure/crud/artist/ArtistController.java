package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/artists")
class ArtistController {

    private final SongifyCrudFacade songifyCrudFacade;

    @GetMapping
    ResponseEntity<AllArtistsDto> findAllArtists(Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtistsDto(pageable);
        AllArtistsDto allArtistsDto = AllArtistsDto.builder()
                                                   .artists(allArtists)
                                                   .build();
        return ResponseEntity.ok(allArtistsDto);
    }

    @PostMapping("/album/songs")
    ResponseEntity<ArtistDto> addArtistWithDefaultAlbumWithDefaultSongs(@RequestBody ArtistRequestDto artistRequestDto) {
        return ResponseEntity.ok(songifyCrudFacade.addArtistWithDefaultAlbumAndSongs(artistRequestDto));
    }

    @PostMapping
    ResponseEntity<ArtistDto> addArtist(@RequestBody ArtistRequestDto artistRequestDto) {
        return ResponseEntity.ok(songifyCrudFacade.addArtist(artistRequestDto));
    }

    @DeleteMapping("/{artistId}")
    ResponseEntity<String> deleteArtistById(@PathVariable final Long artistId) {
        songifyCrudFacade.deleteArtistByIdWithSongsAndAlbums(artistId);
        return ResponseEntity.ok("Artist with id: " + artistId + " deleted");
    }

    @PutMapping("/{artistId}/albums/{albumId}")
    public ResponseEntity<String> addArtistToAlbum(@PathVariable final Long artistId,
                                                   @PathVariable final Long albumId) {
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        return ResponseEntity.ok("Artist with id: " + artistId + " added to album with id: " + albumId);
    }

    @PatchMapping("/{artistId}")
    public ResponseEntity<ArtistDto> updateArtistNameById(@PathVariable final Long artistId,
                                                          @Valid @RequestBody final ArtistRequestUpdateDto artistRequestUpdateDto) {
        return ResponseEntity.ok(songifyCrudFacade.updateArtistNameById(artistId, artistRequestUpdateDto.name()));
    }
}