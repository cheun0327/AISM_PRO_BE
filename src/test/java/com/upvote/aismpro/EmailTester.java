package com.upvote.aismpro;


import com.upvote.aismpro.controller.EmailController;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
// 특정 빈만 로드한다.
//@WebMvcTest(EmailController.class)
@AutoConfigureMockMvc
public class EmailTester {

    @Autowired
    MockMvc mvc;
//    @MockBean
//    EmailController emailController

    @Test
    public void 구글_인증코드_보내기() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Gson gson = new Gson();

        map.put("email", "cheun0327@gmail.com");

        MockHttpServletRequestBuilder request = post("/email")
                .param("email", "cheun0327@gmail.com");


        mvc.perform(post("/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(map)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
