package com.upvote.aismpro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyLibrarySearchDTO {
    private String search;
    private String sort;
    private String category;
}
