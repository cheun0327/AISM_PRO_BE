package com.upvote.aismpro.dto;


import com.upvote.aismpro.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class NewSongDTO {

    private String songId;

    private String songName;

    private String creatorName;

    private Date createDate;

    private String fileName;

    private List<String> tag;

    private String playtime;

    private String thumbnail;

    private Boolean like;
}
