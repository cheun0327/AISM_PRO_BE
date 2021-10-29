package com.upvote.aismpro.entity;

import com.upvote.aismpro.repository.PlaylistLikeRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class PlaylistLike {

    @Id
    @Column(name = "playlistLikeId")
    private String Id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "playlistId")
    private PlayList playlist;

    public PlaylistLike(User user, PlayList playlist) {
        this.Id = UUID.randomUUID().toString();
        this.user = user;
        this.playlist = playlist;
    }
}
