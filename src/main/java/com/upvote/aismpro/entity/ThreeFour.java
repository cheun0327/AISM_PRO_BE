package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "three_four")
@IdClass(ThreeFourPK.class)
public class ThreeFour {
    @Id
    private String three;

    @Id
    private String four;
}
