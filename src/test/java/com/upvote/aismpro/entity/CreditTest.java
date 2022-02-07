package com.upvote.aismpro.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.repository.CreditRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class CreditTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private CustomModelMapper customModelMapper;

    @Test
    public void insertCredit() {

        List<Credit> credits = userRepository.findAll().stream()
                .map(user -> Credit.builder()
                        .user(user)
                        .credit(50000L)
                        .build()).collect(Collectors.toList());

        creditRepository.saveAll(credits);
    }

    @Test
    public void testDto() throws JsonProcessingException {

        User user = userRepository.findById(60L)
                .orElseThrow(IllegalArgumentException::new);

        UserDTO dto = customModelMapper.toUserDTO().map(user, UserDTO.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);

        System.out.println(json);
    }
}