package com.upvote.aismpro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PlaylistDetailDTO {
    private String playlistId;
    private String playlistCreatorId;
    private String playlistCreatorName;
    private String playlistName;
    private String playlistState;
    private String playlistImg;
    private List<SongDTO> songs;
    private List<String> keywords;
}
