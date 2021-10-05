package com.upvote.aismpro.dto;

import lombok.Data;

import java.util.List;

@Data
public class SongBarDTO {
    private String thumbnail;
    private String songName;
    private String creator;
    private List<String> tag;
    private String songId;
    private String fileName;

}
