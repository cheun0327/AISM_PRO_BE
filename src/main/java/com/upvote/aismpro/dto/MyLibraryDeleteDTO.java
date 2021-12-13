package com.upvote.aismpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyLibraryDeleteDTO {
    private String category;
    private List<Long> deleteList;
}
