package com.upvote.aismpro.dto;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MyLibraryDeleteDTO {
    private String category;
    private List<Long> deleteList;
}
