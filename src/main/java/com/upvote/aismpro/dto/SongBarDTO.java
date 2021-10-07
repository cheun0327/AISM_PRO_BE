package com.upvote.aismpro.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class SongBarDTO {
    private String thumbnail;
    private String songName;
    private String creator;
    private List<String> tag;
    private String songId;
    private String fileName;

    public void print(){
        System.out.println(this.songId);
        System.out.println(this.songName);
        System.out.println(this.creator);
        System.out.println(this.tag);
        System.out.println(this.thumbnail);
        System.out.println(this.fileName);
    }

}
