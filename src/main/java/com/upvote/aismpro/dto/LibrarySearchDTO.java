package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LibrarySearchDTO {
    private String search;
    private List<String> type;
    private List<String> length;
    private List<String> genre;
    private List<String> firstMood;
    private List<String> secondMood;

    public LibrarySearchDTO(String search, List<String> type, List<String> length, List<String> genre, List<String> firstMood, List<String> secondMood){
        this.search = search;
        this.type = type;
        this.length = length;
        this.genre = genre;
        this.firstMood = firstMood;
        this.secondMood = secondMood;
    }

    public void print(){
        System.out.println(this.search);
        System.out.println(this.type);
        System.out.println(this.length);
        System.out.println(this.genre);
        System.out.println(this.firstMood);
        System.out.println(this.secondMood);
    }
}
