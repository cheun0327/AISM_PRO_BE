package com.upvote.aismpro.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class PlaylistSaveVO {

    private String val;
    private MultipartFile img;

}