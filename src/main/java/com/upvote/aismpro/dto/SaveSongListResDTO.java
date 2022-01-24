package com.upvote.aismpro.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SaveSongListResDTO {

    private final List<SongDTO> duplicateSongList;
    private final List<SongDTO> savedSongList;
}
