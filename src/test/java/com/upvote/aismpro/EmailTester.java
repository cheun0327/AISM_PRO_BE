package com.upvote.aismpro;


import com.upvote.aismpro.controller.EmailController;

import com.google.gson.Gson;
import com.upvote.aismpro.service.EmailService;
import com.upvote.aismpro.service.EmailServiceInter;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
// 특정 빈만 로드한다.
//@WebMvcTest(EmailController.class)
@AutoConfigureMockMvc
public class EmailTester {
    //https://itmore.tistory.com/entry/MockMvc-%EC%83%81%EC%84%B8%EC%84%A4%EB%AA%85

    // EmailController 관련 의존성만 로드.
    @Autowired
    private MockMvc mvc;

    // controller DI : 테스트 대상 컨트롤러에 지정
    @InjectMocks
    EmailController controller;

    // Mock 객체로 실제 객체가 아니지만, 실제 객체처럼 동작하게 만들 수 있다.
    // 테스트 대상 컨트롤러(@InjectMocks)에 대해 모의화한 컴포넌트(@Mock)를 인잭션
    @Mock
    private EmailService email;

    @Test
    public void 구글_인증코드_보내기() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Gson gson = new Gson();

        mvc = MockMvcBuilders.standaloneSetup(controller).build();


        map.put("email", "cheun0327@gmail.com");

        //email.sendSimpleMessage("cheun0327@gmail.com");

        this.mvc.perform(post("/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(map)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
