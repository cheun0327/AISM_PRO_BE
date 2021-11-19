package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "platform", nullable = false)
    private String platform;

    // song처럼 그냥 그때그때 조합해서 쓸지 고민해봥
    @Column(name = "profile", nullable = false)
    private String profile;

//    @OneToMany(mappedBy = "user")
//    @JsonBackReference
//    private List<Create> creates = new ArrayList<Create>();
//
//    @OneToMany(mappedBy = "user")
//    @JsonBackReference
//    private List<Buy> buys = new ArrayList<Buy>();
//
//    @OneToMany(mappedBy = "user")
//    @JsonBackReference
//    private List<Sell> sells = new ArrayList<Sell>();
//
//    @OneToMany(mappedBy = "user")
//    @JsonBackReference
//    private List<Like> likes = new ArrayList<Like>();
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JsonBackReference
//    private List<PERSISTlayList> playlists = new ArrayList<PlayList>();

    @Builder
    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public User(String nickname, String email, String platform) {
        this.nickname = nickname;
        this.email = email;
        this.platform = platform;
    }

    public User(String nickname, String email, String platform, String profile) {
        this.nickname = nickname;
        this.email = email;
        this.platform = platform;
        this.profile = profile;
    }

}
