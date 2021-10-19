package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name="songId", nullable = false)
    private String songId;

    @Column(nullable = false)
    private String createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "creatorId", referencedColumnName="userId")
    private User user;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String fileName;

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

    public void print() {
        System.out.println("songId : " + this.songId);
        System.out.println("createDate : " + this.createDate);
        System.out.println("songName : " + this.songName);
        System.out.println("fileName : " + this.fileName);
    }
}

