package com.upvote.aismpro.dto;

import lombok.Data;

@Data
public class ComposeInfo {
    private String length;
    private String genre;
    private String mood1;
    private String mood2;

    public ComposeInfo(String length, String genre, String mood1, String mood2){
        this.length = length;
        this.genre = genre;
        this.mood1 = mood1;
        this.mood2 = mood2;
    }
}
