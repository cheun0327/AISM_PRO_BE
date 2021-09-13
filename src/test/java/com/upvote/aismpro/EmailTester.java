package com.upvote.aismpro;


import com.upvote.aismpro.controller.EmailController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmailController.class)
public class EmailTester {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 구글_인증코드_보내기() throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("email", "cheun0327@gmail.com");

        mvc.perform(post("/email"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
