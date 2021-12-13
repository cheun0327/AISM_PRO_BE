package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "likes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @Column(name="likeId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "songId")
    @JsonManagedReference
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creatorId")
    @JsonManagedReference
    private User user;


    public Like(User user, Song song) {
        this.user = user;
        this.song = song;
    }

}
