package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewPlaylistDTO {
    private String playlistId;
    private String playlistName;
    private String playlistCreatorName;
    private String playlistState;
    private String playlistImg;
    private Boolean playlistLike;
    private Integer playlistSongCount;
    private Integer playlistPlaytime;
}
