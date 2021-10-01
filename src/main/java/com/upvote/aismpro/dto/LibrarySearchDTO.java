package com.upvote.aismpro.dto;

import lombok.Data;

import java.util.List;

@Data
public class LibrarySearchDTO {
    private List<String> type;
    private List<String> length;
    private List<String> genre;
    private List<String> mood1;
    private List<String> mood2;

    public LibrarySearchDTO(List<String> type, List<String> length, List<String> genre, List<String> mood1, List<String> mood2){
        this.type = type;
        this.length = length;
        this.genre = genre;
        this.mood1 = mood1;
        this.mood2 = mood2;
    }
}
