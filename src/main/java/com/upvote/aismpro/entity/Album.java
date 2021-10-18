package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@IdClass(AlbumPK.class)
@NoArgsConstructor
@Table(name = "Album")
@Data
public class Album {
    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId")
    private User user;

    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "songId")
    private Song song;

    @Column(nullable = false)
    private String authority;
}
