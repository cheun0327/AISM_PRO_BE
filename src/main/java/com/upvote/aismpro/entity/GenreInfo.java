package com.upvote.aismpro.entity;

import com.upvote.aismpro.entitypk.KeywordPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "genre_info")
@NoArgsConstructor
public class GenreInfo {
    @Id
    @Column(name = "genre")
    private String genre;

    @Column(name = "idx")
    private Integer idx;

    @Column(name = "one")
    private String one;

    @Column(name="two")
    private String two;

    @Column(name="three")
    private String three;

    @Column(name="four")
    private String four;

    @Column(name="five")
    private String five;

    @Column(name="six")
    private String six;
}
