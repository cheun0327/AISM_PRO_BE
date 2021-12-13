package com.upvote.aismpro.security;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class JWTController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomModelMapper modelMapper;


    // 프론트에서 토큰 인식 요청
    @GetMapping("/token/auth")
    public ResponseEntity<Boolean> validToken() {
        try {
            if (SecurityUtil.getCurrentUserId() == -1) {throw new Exception();}
            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    // 로그인 유지
    // 토큰으로 사용자 정보 가져오기
    @GetMapping("/token/subject")
    public ResponseEntity<UserDTO> getUserByToken() {
         try {
            Long userId = SecurityUtil.getCurrentUserId();
            if (userId == -1) {throw new Exception();}
            User user = userRepository.getById(userId);
            return new ResponseEntity<>(modelMapper.toUserDTO().map(user, UserDTO.class), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
