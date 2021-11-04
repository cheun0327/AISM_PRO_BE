package com.upvote.aismpro;

import com.google.gson.Gson;
import com.upvote.aismpro.controller.LibraryController;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LibraryTester {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private LibraryController libraryController;

    @Mock
    private LibraryService libraryService;

    @Test
    public void 라이브러리_검색() throws Exception {
        Map<String, Object> map  = new HashMap<>();
        Gson gson = new Gson();

        mvc = MockMvcBuilders.standaloneSetup(libraryController).build();

        LibrarySearchDTO dto = new LibrarySearchDTO(
                "",
                new ArrayList<String>(Arrays.asList("song", "sample")),
                new ArrayList<String>(Arrays.asList("30", "60")),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        MvcResult result = this.mvc.perform(post("/library/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(dto)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }
}
