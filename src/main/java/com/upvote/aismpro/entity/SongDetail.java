package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "song_detail")
@Data
public class SongDetail {

    @javax.persistence.Id
    @Column(nullable = false)
    private String songId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="songId")
    @JsonBackReference
    private Song song;

    @Column
    private String genre;

    @Column
    private String mood1;

    @Column
    private String mood2;

    @Column
    private String mood3;

    @Column
    private String type;

    @Column
    private String length;
}
