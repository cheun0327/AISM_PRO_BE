package com.upvote.aismpro.dto;

import lombok.Data;

import java.util.List;

@Data
public class LibrarySearchDTO {
    private List<String> Type;
    private List<String> Length;
    private List<String> Genre;
    private List<String> First_mood;
    private List<String> Second_mood;

    public LibrarySearchDTO(List<String> Type, List<String> Length, List<String> Genre, List<String> First_mood, List<String> Second_mood){
        this.Type = Type;
        this.Length = Length;
        this.Genre = Genre;
        this.First_mood = First_mood;
        this.Second_mood = Second_mood;
    }

    public void toString(List<String> type, List<String> length, List<String> genre, List<String> first_mood, List<String> second_mood){
        System.out.println(type);
        System.out.println(length);
        System.out.println(genre);
        System.out.println(first_mood);
        System.out.println(second_mood);
    }
}
