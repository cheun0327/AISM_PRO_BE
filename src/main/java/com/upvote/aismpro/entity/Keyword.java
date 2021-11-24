package com.upvote.aismpro.entity;

import com.upvote.aismpro.entitypk.KeywordPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "keyword")
@NoArgsConstructor
@IdClass(KeywordPK.class)
public class Keyword {
    @Id
    @Column(name="one")
    private String one;

    @Id
    @Column(name="two")
    private String two;

    @Id
    @Column(name="three")
    private String three;

    @Id
    @Column(name="four")
    private String four;

    @Id
    @Column(name="five")
    private String five;

    @Id
    @Column(name="six")
    private String six;
}
