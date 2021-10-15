package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Album")
@Data
@IdClass(AlbumPK.class)
public class Album {
    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "songId", referencedColumnName = "songId")
    private Song song;

    @Column(nullable = false)
    private String authority;
}
