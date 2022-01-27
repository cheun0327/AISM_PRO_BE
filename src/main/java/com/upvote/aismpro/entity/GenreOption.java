package com.upvote.aismpro.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "genre_options")
@Entity
public class GenreOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "option_name", nullable = false, unique = true)
    private String optionName;
}
