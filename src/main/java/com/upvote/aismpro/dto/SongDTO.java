package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
public class SongDTO {
    private Long songId;

    private String songName;

    private Long creatorId;

    private String creatorName;

    private Timestamp createDate;

    private List<String> tags;

    private String playtime;

    private String filePath;

    private String imgPath;

    private Boolean like;
}

