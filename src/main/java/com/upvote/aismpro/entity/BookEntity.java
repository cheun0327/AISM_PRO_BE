package com.upvote.aismpro.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "book")
@Data
public class BookEntity {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long sellCount;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;

    public void display() {
        System.out.println("id : " + id);
        System.out.println("category : " + category);
        System.out.println("name : " + name);
        System.out.println("sellCount : " + sellCount);
        System.out.println("createAt : " + createAt);
    }
}
