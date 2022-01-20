package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlaylistDetailDTO {
    private Long playlistId;
    private String playlistCreatorId;
    private String playlistCreatorName;
    private String playlistName;
    private String playlistState;
    private List<String> playlistImgs;
    private List<SongDTO> songs;
    private List<String> keywords;
}

