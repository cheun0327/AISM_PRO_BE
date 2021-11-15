package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "one_two")
@IdClass(OneTwoPK.class)
public class OneTwo {
    @Id
    @Column(name="one", nullable = false)
    private String one;

    @Id
    @Column(name="two", nullable = false)
    private String two;

    @Column(name="indx")
    private Integer indx;
}
