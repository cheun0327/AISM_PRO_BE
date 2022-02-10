package com.upvote.aismpro.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "fx_names")
@Entity
public class FX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fx_name_id")
    private Long id;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "sample_file_name")
    private String sampleFileName;

    @Column(name = "real_file_name")
    private String realFileName;
}
