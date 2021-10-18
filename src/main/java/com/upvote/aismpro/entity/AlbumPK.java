package com.upvote.aismpro.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AlbumPK implements Serializable {
    private String user;
    private String song;

    public AlbumPK(String userID, String songID) {
        this.user = userID;
        this.song = songID;
    }
}
