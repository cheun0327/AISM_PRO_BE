package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Table(name = "new_song")
@Data
public class NewSong {

    @Id
    @Column(name="songId", nullable = false)
    private String songId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId", referencedColumnName="userId")
    private User user;

    @Column(nullable = false)
    private String songName;

    @Column(nullable = false)
    private String fileName;

    @Column
    private String one;

    @Column
    private String two;

    @Column
    private String three;

    @Column
    private String four;

    @Column
    private String five;

    @Column
    private String type;

    @Column
    private String playtime;

    @Column
    private String thumbnail;
}
