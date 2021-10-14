package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "song")
@Data
public class Song {
    @Id
    @Column(name="songId", nullable = false)
    private String songId;

    @Column(nullable = false)
    private String createDate;

    @Column(nullable = false)
    private String creatorID;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String fileName;
}
