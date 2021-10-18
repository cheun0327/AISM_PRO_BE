package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.Song;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PlaylistDTO {
    private String playlistId;
    private String creatorId;
    private String name;
    private String state;
    private String img;
    //private List<SongBarDTO> songs;
}
