package com.upvote.aismpro.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlaylistDTO {
    private Long playlistId;
    private String playlistName;
    private String playlistCreatorName;
    private String playlistState;
    private List<String> playlistImgs;
    private Integer playlistSongCount;
    private Integer playlistPlaytime;
}
