package com.upvote.aismpro.entity;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;

@Data
@Entity
@Getter
@Table(name="user_info")
//@AllArgsConstructor -> 생성자를 알아서 만들어준다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "userPwd", nullable = false)
    private String userPwd;

    // 안전하게 객체 생성하기
    @Builder
    public User(String userId, String userName, String userPwd) {
        Assert.hasText(userId, "userId must not be empty");
        Assert.hasText(userName, "userName must not be empty");
        Assert.hasText(userPwd, "userPwd must not be empty");

        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    //TODO setter를 어떻게 처리해줄지 고민해보기! lombok은 지양하되, 남용을 최대한 막지만, 편리하게사용할 수 있는 방향성 지향
}
