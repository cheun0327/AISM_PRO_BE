package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "compose")
@Data
@IdClass(ComposePK.class)
public class Compose {
    @Id
    @Column(nullable = false)
    private String genre;

    @Id
    @Column(nullable = false)
    private String first_mood;

    @Id
    @Column(nullable = false)
    private String second_mood;

    @Id
    @Column(nullable = false)
    private String sample;
}
