package com.upvote.aismpro.entity;

import com.upvote.aismpro.entitypk.KeywordPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "keyword")
@NoArgsConstructor
public class Keyword {

    @Id
    @Column(name="id")
    private Long id;


    @Column(name="one")
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
