package com.upvote.aismpro.dto;

import com.upvote.aismpro.entity.Song;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PlaylistDTO {
    private String playlistId;
    private String creatorId;
    private String name;
    private String state;
    private String img;

    public void print() {
        System.out.println("pl id : " + this.playlistId);
        System.out.println("creator id : " + this.creatorId);
        System.out.println("name " + this.name);
        System.out.println("state : " + this.state);
        System.out.println("img : " + this.img);
    }
}
