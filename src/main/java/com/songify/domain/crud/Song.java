package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Entity
@Builder
@Getter
@Setter
@Table(name = "songs")
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class Song extends BaseEntity {

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

    private Instant releaseDate;

    private Long duration;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    @OneToOne
    private Genre genre;

    public Song(final String nameToUpdate) {
        this.name = nameToUpdate;
    }
}