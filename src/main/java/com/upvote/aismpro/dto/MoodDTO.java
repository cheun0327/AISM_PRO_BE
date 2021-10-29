package com.upvote.aismpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MoodDTO {
    private String songId;
    private String genre;
    private String firstMood;
    private String secondMood;
    private String thirdMood;
}
