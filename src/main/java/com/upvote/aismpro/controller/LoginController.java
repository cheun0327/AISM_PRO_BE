package com.upvote.aismpro.controller;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.TokenDTO;
import com.upvote.aismpro.security.TokenProvider;
import com.upvote.aismpro.service.LoginService;
import com.upvote.aismpro.sns.GoogleTokenVerifier;
import com.upvote.aismpro.sns.NaverTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    GoogleTokenVerifier googleVerifier;
    @Autowired
    NaverTokenVerifier naverTokenVerifier;
    @Autowired
    private LoginService loginService;

    // 카카오 로그인 정보 받음
    @PostMapping("/login/kakao")
    public ResponseEntity<LoginUserDTO> kakaoLogin(HttpServletRequest request, @RequestBody LinkedHashMap<String, Object> kakaoInfo) throws Exception {
        // 카카오 로그인 정보 json
        LinkedHashMap<String, Object> kakaoProfile = (LinkedHashMap<String, Object>) kakaoInfo.get("profile");
        // 카카오 로그인 유저 정보 json
        LinkedHashMap<String, Object> kakaoProfileInfo = (LinkedHashMap<String, Object>) kakaoProfile.get("kakao_account");

        User user = loginService.checkUser("카카오", (String) kakaoProfileInfo.get("email"));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getEmail());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);

        TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

        // TODO refresh token 저장

        return new ResponseEntity<>(new LoginUserDTO(tokenDTO.getAccessToken(), user), HttpStatus.OK);

    }

    @PostMapping("/login/google")
    public ResponseEntity<LoginUserDTO> googleLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody LinkedHashMap<String, Object> googleInfo) {
        // 구글 로그인 정보 json
        LinkedHashMap<String, String> googleProfile = (LinkedHashMap<String, String>) googleInfo.get("profile");

        try {
            User user = loginService.checkUser("구글", googleProfile.get("email"));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getEmail());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);

            TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

            // TODO refresh token 저장

            return new ResponseEntity<>(new LoginUserDTO(tokenDTO.getAccessToken(), user), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/login/naver")
//    public @ResponseBody Map<String, Object> naverLogin(HttpServletRequest request, @RequestParam("access_token") String access_token) throws IOException {
//        Map<String, Object> naverProfile = naverTokenVerifier.getUserInfo(access_token);
//
//        try {
//            String userId = loginService.snsLinkageCheck("네이버", (String) ((String) naverProfile.get("email")).replace("\"", ""));
//            Map<String, Object> map = new HashMap<>();
//
//            // sns 로그인 정보로 og 회원 정보 가져오기
//            User user = loginService.getUserInfo(userId);
//
//            //userId로 token 생성
//            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));
//
//            Map<String, String> data = new HashMap<String, String>() {{
//                put("token", token);
//                put("userId", userId);
//                put("userEmail", user.getEmail());
//                put("userNickName", user.getNickname());
//            }};
//            map.put("result", true);
//            map.put("data", data);
//
//            return map;
//        } catch (EntityNotFoundException e){
//            e.printStackTrace();
//            // Session 설정
//            HttpSession tmpSession = request.getSession();
//
//            tmpSession.setAttribute("platform", "네이버");
//            tmpSession.setAttribute("snsEmail", naverProfile.get("email"));
//
//            System.out.println(tmpSession.getAttribute("platform").toString() + tmpSession.getAttribute("snsEmail").toString());
//
//            return Collections.singletonMap("result", false);
//        }
//    }

    // 로그인 성공 이후 사용자 정보 전달
    @GetMapping("/getUserInfo")
    public User getUserInfo(@RequestParam("userID") Long userID) {
        return loginService.getUserInfo(userID);
    }
}
