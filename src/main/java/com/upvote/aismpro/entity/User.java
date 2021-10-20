package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Create> creates = new ArrayList<Create>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Buy> buys = new ArrayList<Buy>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Sell> sells = new ArrayList<Sell>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Like> likes = new ArrayList<Like>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<PlayList> playlists = new ArrayList<PlayList>();

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

    // create 추가
    public void addCreate(Create create) {
        this.creates.add(create);
        if (create.getUser() != this) create.setUser(this);
    }

    // buy 추가
    public void addBuy(Buy buy) {
        this.buys.add(buy);
        if (buy.getUser() != this) buy.setUser(this);
    }

    // sell 추가
    public void addSell(Sell sell) {
        this.sells.add(sell);
        if (sell.getUser() != this) sell.setUser(this);
    }

    // like 추가
    public void addLike(Like like) {
        this.likes.add(like);
        if (like.getUser() != this) like.setUser(this);
    }

    public void setPlaylistList(List<PlayList> playlists) {
        this.playlists = playlists;
        if (this.playlists != null && this.playlists.size() > 0) {
            for (PlayList pl : playlists)   pl.setUser(this);
        }
    }

    public void print() {
        System.out.println("id : " + this.id);
        System.out.println("email : " + this.email);
        System.out.println("nickname : " + this.nickName);
    }
}