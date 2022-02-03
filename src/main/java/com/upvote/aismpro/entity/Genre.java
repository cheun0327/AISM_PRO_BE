package com.upvote.aismpro.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "genres")
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "genre_name", nullable = false, unique = true)
    private String genreName;

    @Column(name = "genre_order")
    private Integer genreOrder;

    @Builder
    public Genre(String genreName, Integer genreOrder) {
        this.genreName = genreName;
        this.genreOrder = genreOrder;
    }
}
