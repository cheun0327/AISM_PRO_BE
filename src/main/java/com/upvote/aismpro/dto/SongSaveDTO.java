package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongSaveDTO {

    private String songName;
    private String playtime;
    private String type;

    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    private String six;

}
