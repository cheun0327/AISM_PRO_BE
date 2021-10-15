package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId", referencedColumnName = "userId")
    private User user;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String fileName;
}