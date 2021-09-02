package com.upvote.aismpro.book;


import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Date;


@Entity
public class User {

    @Id @GeneratedValue
    private  Long id;

    private  String name;


    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
