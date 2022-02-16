package com.upvote.aismpro.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "dummy_keyword_paths")
@Entity
public class DummyKeywordPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dummy_keyword_id")
    private Long id;

    @Column(name = "mood1")
    private String mood1;

    @Column(name = "mood2")
    private String mood2;

    @Column(name = "mood3")
    private String mood3;
}
