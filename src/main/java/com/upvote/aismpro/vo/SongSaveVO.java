package com.upvote.aismpro.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class SongSaveVO {

    private String val;
    private MultipartFile img;

}
