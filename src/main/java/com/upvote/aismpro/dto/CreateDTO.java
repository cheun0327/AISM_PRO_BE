package com.upvote.aismpro.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateDTO {
    private String songId;
    private String songName;
    private String creatorName;
    private List<String> tag;
    private String fileName;
    private String thumbnail;
    private Date createDate;
    private String genre;
    private String length;

    public void print() {
        System.out.println(this.songId);
        System.out.println(this.songName);
        System.out.println(this.creatorName);
        System.out.println(this.tag);
        System.out.println(this.thumbnail);
        System.out.println(this.fileName);
        System.out.println(this.createDate);
        System.out.println(this.genre);
        System.out.println(this.length);
    }
}
