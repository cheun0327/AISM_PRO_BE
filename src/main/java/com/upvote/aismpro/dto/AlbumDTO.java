package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlbumDTO {
    private User user;
    private Song song;
    private String authority;
}
