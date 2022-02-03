package com.upvote.aismpro.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "genre_option_orders")
@Entity
public class GenreOptionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private GenreOption genreOption;

    @Column(name = "option_order")
    private Integer order;

    @Builder
    public GenreOptionOrder(Genre genre, GenreOption genreOption, Integer order) {
        this.genre = genre;
        this.genreOption = genreOption;
        this.order = order;
    }
}
