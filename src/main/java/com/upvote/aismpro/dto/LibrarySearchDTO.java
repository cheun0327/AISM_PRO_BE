package com.upvote.aismpro.dto;

import lombok.Data;

import java.util.List;

@Data
public class LibrarySearchDTO {
    private List<String> Type;
    private List<String> Length;
    private List<String> Genre;
    private List<String> First_Mood;
    private List<String> Second_Mood;

    public LibrarySearchDTO(List<String> Type, List<String> Length, List<String> Genre, List<String> First_Mood, List<String> Second_Mood){
        this.Type = Type;
        this.Length = Length;
        this.Genre = Genre;
        this.First_Mood = First_Mood;
        this.Second_Mood = Second_Mood;
    }

    public void print(){
        System.out.println(this.Type);
        System.out.println(this.Length);
        System.out.println(this.Genre);
        System.out.println(this.First_Mood);
        System.out.println(this.Second_Mood);
    }
}
