package com.upvote.aismpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class SongSaveDTO {
    private String songName;

    private String playtime;

    private String type;

    private MultipartFile img;

    private String one;

    private String two;

    private String three;

    private String four;

    private String five;

    private String six;

}
