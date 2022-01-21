package com.upvote.aismpro.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class SongListForAddToPlaylistDTO {

    private final Boolean isEnough;
    private final List<SongDTO> songs;
}
