package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "user")
@Data
public class User {

    @Id
    @Column(name = "userId", nullable = false)
    private String id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Song> songs = new ArrayList<Song>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<PlayList> playlists = new ArrayList<PlayList>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Album> albums = new ArrayList<Album>();

    @Builder
    public User(String nickName, String email, String picture) {
        this.nickName = nickName;
        this.email = email;
    }

    // playlist 추가
    public void addPlaylist(PlayList playlist) {
        this.playlists.add(playlist);
        if (playlist.getUser() != this) playlist.setUser(this);
    }

    public void print() {
        System.out.println("id : " + this.id);
        System.out.println("email : " + this.email);
        System.out.println("nickname : " + this.nickName);
    }
}
