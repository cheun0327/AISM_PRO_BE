package com.upvote.aismpro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "creates")
@NoArgsConstructor
public class Create {

    @Id
    @Column(name = "createId", nullable = false)
    private Long createId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "songId")
    @JsonManagedReference
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="creatorId", referencedColumnName="userId")
    @JsonManagedReference
    private User user;

}
