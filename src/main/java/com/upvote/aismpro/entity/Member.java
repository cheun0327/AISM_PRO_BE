package com.upvote.aismpro.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="MEMBER")
//@AllArgsConstructor -> 생성자를 알아서 만들어준다.
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<Order>();

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

    public Address getAddress() {
        return address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // 안전하게 객체 생성하기
    /*
    @Builder
    public Member(Long id, String name, String password) {
        Assert.has(id, "id must not be empty");
        Assert.hasText(name, "name must not be empty");
        Assert.hasText(password, "password must not be empty");

        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
    }
    */


    //TODO setter를 어떻게 처리해줄지 고민해보기! lombok은 지양하되, 남용을 최대한 막지만, 편리하게사용할 수 있는 방향성 지향
}
