package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "creatorID", referencedColumnName="userId")
    private User user;

    @OneToOne(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference
    private SongDetail songDetail;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String fileName;

    // user 추가
    public void setUser (User user) {
        this.user = user;
        user.getSongs().add(this);
    }

    public void print() {
        System.out.println("songId : " + this.songId);
        System.out.println("createDate : " + this.createDate);
        System.out.println("songName : " + this.songName);
        System.out.println("fileName : " + this.fileName);
    }
}

