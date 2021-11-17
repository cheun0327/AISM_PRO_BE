package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
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
    private Timestamp createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId", referencedColumnName="userId")
    private User user;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String fileName;

    @Column
    private String genre;

    @Column
    private String firstMood;

    @Column
    private String secondMood;

    @Column
    private String thirdMood;

    @Column
    private String type;

    @Column
    private String length;

    @Column
    private String thumbnail;

    public Song(String songId, String songName, String fileName,
                 String genre, String firstMood, String secondMood, String thirdMood,
                 String type, String length, User user, Timestamp createDate){
        this.songId = songId;
        this.songName = songName;
        this.fileName = fileName;
        this.genre = genre;
        this.firstMood = firstMood;
        this.secondMood = secondMood;
        this.thirdMood = thirdMood;
        this.type = type;
        this.length = length;
        this.user = user;
        this.createDate = createDate;
    }

    public void print() {
        System.out.println("songId : " + this.songId);
        System.out.println("createDate : " + this.createDate);
        System.out.println("songName : " + this.songName);
        System.out.println("fileName : " + this.fileName);
    }
}
