package com.upvote.aismpro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDTO{
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
}

