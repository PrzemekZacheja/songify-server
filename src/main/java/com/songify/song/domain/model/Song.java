package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@Table(name = "songs")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Song extends BaseEntity{

    @Id
    @GeneratedValue(generator = "songs_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "songs_id_seq",
            sequenceName = "songs_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String artist;

    private Instant releaseDate;

    private Long duration;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }
}