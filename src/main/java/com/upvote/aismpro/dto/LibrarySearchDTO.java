package com.upvote.aismpro.dto;

import lombok.Data;

@Data
public class LibrarySearchDTO {
    private String type;
    private String length;
    private String genre;
    private String mood1;
    private String mood2;

    public LibrarySearchDTO(String type, String length, String genre, String mood1, String mood2){
        this.type = type;
        this.length = length;
        this.genre = genre;
        this.mood1 = mood1;
        this.mood2 = mood2;
    }
}
