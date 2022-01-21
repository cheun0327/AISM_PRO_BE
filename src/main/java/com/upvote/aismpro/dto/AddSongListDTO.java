package com.upvote.aismpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddSongListDTO {

    private Long playlistId;
    private List<Long> songIdList;
}
