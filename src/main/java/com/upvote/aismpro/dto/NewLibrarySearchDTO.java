package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class NewLibrarySearchDTO {
    private String userId;
    private String search;
    private String type;
    private String sort;
    private List<String> genre;
    private List<String> inst;
    private List<String> mood;
    private List<String> playtime;
    private List<String> tempo;

    public NewLibrarySearchDTO(String userId, String search, String type, String sort, List<String> genre, List<String> inst, List<String> mood, List<String> playtime, List<String> tempo){
        this.userId = userId;
        this.search = search;
        this.type = type;
        this.sort = sort;
        this.genre = genre;
        this.inst = inst;
        this.mood = mood;
        this.playtime = playtime.stream().map(p->typeConversionPlaytime(p)).collect(Collectors.toList());
        this.tempo = tempo;
    }

    private String typeConversionPlaytime(String playtime) {
        switch (playtime) {
            case "30초": return "30";
            case "1분": return "60";
            case "1분 30초": return "90";
            case "2분": return "120";
            case "2분 30초": return "150";
            case "3분": return "180";
            case "3분 30초": return "210";
            case "4분": return "240";
            case "4분 30초": return "270";
            case "5분": return "300";
            default: return null;
        }
    }
}
