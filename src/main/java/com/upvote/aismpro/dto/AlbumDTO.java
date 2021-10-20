package com.upvote.aismpro.dto;

import com.querydsl.core.types.dsl.StringPath;
import com.upvote.aismpro.entity.QSong;
import com.upvote.aismpro.entity.QUser;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AlbumDTO {
//    private String userID;
//    private String nickName;
//    private String email;
//    private String songID;
//    private String createDate;
//    private String creatorID;
//    private String songName;
//    private String fileName;
//    private String authority;

    private User user;
    private Song song;
    private String authority;
}