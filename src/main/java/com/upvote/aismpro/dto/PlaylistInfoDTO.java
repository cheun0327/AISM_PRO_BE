package com.upvote.aismpro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaylistInfoDTO {
    private String playlistCreatorName;
    private String playlistName;
    private String playlistState;
    private String playlistImg;
    private String songCreateDate;
    private String songCreatorID;
    private String songCreatorName;
    private String songName;
    private String fileName;

    public PlaylistInfoDTO(String playlistCreatorName,
                           String playlistName,
                           String playlistState,
                           String playlistImg,
                           String songCreateDate,
                           String songCreatorID,
                           String songName,
                           String fileName) {
        this.playlistCreatorName = playlistCreatorName;
        this.playlistName = playlistName;
        this.playlistState = playlistState;
        this.playlistImg = playlistImg;
        this.songCreateDate = songCreateDate;
        this.songCreatorID = songCreatorID;
        this.songCreatorName = songCreatorName;
        this.songName = songName;
        this.fileName = fileName;
    }

}
