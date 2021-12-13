package com.upvote.aismpro.entity;


import com.upvote.aismpro.entitypk.KeywordPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "song")
@NoArgsConstructor
public class Song {

    @Id
    @Column(name = "songId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long songId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId", referencedColumnName="userId")
    private User user;

    @Column(name = "createDate")
    private Timestamp createDate;

    @Column(name = "songName")
    private String songName;

    @Column(name = "type")
    private String type;

    @Column(name = "playtime")
    private String playtime;

    @Column(name = "imgFile")
    private String imgFile;

    // fileName 이랑 thumbnail 경로 둘다 필요 없음 - userId랑 songId 조합해서 만들면 됨.

    @Column(name = "one")
    private String one;

    @Column(name="two")
    private String two;

    @Column(name="three")
    private String three;

    @Column(name="four")
    private String four;

    @Column(name="five")
    private String five;

    @Column(name="six")
    private String six;
}
