package com.upvote.aismpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class LibrarySearchDTO {

    private Long userId;
    private String search;
    private String type;
    private String sort;

    @NotNull
    private List<String> genre;

    @NotNull
    private List<String> inst;

    @NotNull
    private List<String> mood;

    @NotNull
    private List<String> playtime;

    @NotNull
    private List<String> tempo;
}
