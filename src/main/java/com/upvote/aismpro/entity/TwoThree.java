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
@Table(name = "two_three")
@IdClass(TwoThreePK.class)
public class TwoThree {
    @Id
    private String two;

    @Id
    private String three;
}
