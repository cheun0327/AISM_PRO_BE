package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "song_detail")
@Data
public class SongDetail {

    @javax.persistence.Id
    @Column(nullable = false)
    private String songId;

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
    private int length;
}
