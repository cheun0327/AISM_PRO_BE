package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "buys")
@NoArgsConstructor
public class Buy {

    @Id
    @Column(name="buyId", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "songId")
    @JsonManagedReference
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creatorId", referencedColumnName="userId")
    @JsonManagedReference
    private User user;

}
