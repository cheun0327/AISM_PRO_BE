package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;

import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
public class SongDTO extends EntityModel<SongDTO> {
    private Long songId;

    private String songName;

    private Long creatorId;

    private String creatorName;

    private Timestamp createDate;

    private List<String> tags;

    private String playtime;

    private String wavFile;

    private String imgFile;

    private String midiFile;

    private Boolean like;

    private Boolean isSell;

    private Boolean isBuy;
}

