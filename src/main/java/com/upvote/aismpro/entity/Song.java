package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "song")
@Data
public class Song {
    @Id
    @Column(name = "songId", nullable = false)
    private String Id;

    @Column(nullable = false)
    private String createDate;

    @Column(nullable = false)
    private String creatorID;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String fileName;
}
