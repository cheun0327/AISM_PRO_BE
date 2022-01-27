package com.upvote.aismpro.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "songs")
@Entity
public class Song extends BaseEventEntity {

    @Id
    @Column(name = "song_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Column(name = "deleted_at")
    private LocalDateTime deletedDate;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "type")
    private String type;

    @Column(name = "playtime")
    private String playtime;

    @Column(name = "img_file")
    private String imgFile;

    @Column(name = "one")
    private String one;

    @Column(name = "two")
    private String two;

    @Column(name = "three")
    private String three;

    @Column(name = "four")
    private String four;

    @Column(name = "five")
    private String five;

    @Column(name = "six")
    private String six;
}
