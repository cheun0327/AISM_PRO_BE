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
@Table(name = "four_five")
@IdClass(FourFivePK.class)
public class FourFive {
    @Id
    private String four;

    @Id
    private String five;
}
