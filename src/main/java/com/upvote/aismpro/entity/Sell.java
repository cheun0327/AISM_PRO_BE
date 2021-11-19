package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sells")
@NoArgsConstructor
public class Sell {

    @Id
    @Column(name="sellId", nullable = false)
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
