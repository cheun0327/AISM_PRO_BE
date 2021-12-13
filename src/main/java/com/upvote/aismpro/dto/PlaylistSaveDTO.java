package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaylistSaveDTO {

    private String playlistName;
    private Boolean state;
}
