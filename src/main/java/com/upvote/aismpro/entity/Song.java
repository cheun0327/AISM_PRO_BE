package com.upvote.aismpro.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_path_id")
    private KeywordPath keywordPath;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "type")
    private String type;

    @Column(name = "playtime")
    private String playtime;

    @Column(name = "img_file")
    private String imgFile;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(name = "deleted_at")
    private LocalDateTime deletedDate;

    @Builder
    public Song(
            User user,
            KeywordPath keywordPath,
            String songName,
            String type,
            String playtime,
            String imgFile
    ) {
        this.user = user;
        this.keywordPath = keywordPath;
        this.songName = songName;
        this.type = type;
        this.playtime = playtime;
        this.imgFile = imgFile;
    }

    public void addImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public void addPlayTime(String playtime) {
        this.playtime = playtime;
    }
}
