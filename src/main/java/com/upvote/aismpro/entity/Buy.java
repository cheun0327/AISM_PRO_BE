package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "buys")
@NoArgsConstructor
public class Buy {

    @Id
    @Column(name = "buyId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "songId")
    @JsonManagedReference
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorId", referencedColumnName = "userId")
    @JsonManagedReference
    private User user;

    @Builder
    public Buy(User user, Song song) {
        this.user = user;
        this.song = song;
    }

}
