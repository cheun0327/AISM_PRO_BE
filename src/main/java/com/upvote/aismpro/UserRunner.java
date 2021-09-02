package com.upvote.aismpro;

import com.upvote.aismpro.book.User;
import com.upvote.aismpro.book.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserRunner  implements ApplicationRunner{

    @Autowired
    UserRepository userRe;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user = new User();
        user.setName("wow, JPA");
        user.setCreated(new Date());

        userRe.save(user);
    }
}
