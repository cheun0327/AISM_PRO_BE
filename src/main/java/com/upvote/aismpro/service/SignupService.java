package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.dto.SignupDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.Authority;
import com.upvote.aismpro.security.TokenDTO;
import com.upvote.aismpro.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    public LoginUserDTO signup(SignupDTO signupDTO, MultipartFile file) throws Exception {
        try {
            System.out.println(signupDTO.getEmail());
            List<User> users = userRepository.findAllByPlatformAndEmail(signupDTO.getSns(), signupDTO.getEmail());
            if (users.size() >= 1) throw new IllegalAccessException();

            // 유저 저장
            User user = User.builder()
                    .nickname(signupDTO.getNickname())
                    .email(signupDTO.getEmail())
                    .platform(signupDTO.getSns())
                    .authority(Authority.ROLE_GUEST)
                    .build();
            userRepository.save(user);

            // 프로필 이미지 파일 이름 세팅
            String imgFolder = user.getUserId().toString();
            String imgName = imgFolder + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

            //String dirPath = "/Users/upvote3/chaeeun/dev/react-workspace/AISM_PRO_FE/src/components/content/image/user/" + imgFolder;
            String dirPath = "/var/lib/jenkins/workspace/img/profile/" + imgFolder;

            File profileDir = new File(dirPath);

            if (!profileDir.exists())   profileDir.mkdir();
            file.transferTo(new File(dirPath + "/" + imgName));

            user = userRepository.save(user.setProfile(imgFolder + "/" + imgName));

            //user token 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getEmail());
            
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);

            TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

            LoginUserDTO loginUserDTO = new LoginUserDTO(tokenDTO.getAccessToken(), user);

            return loginUserDTO;

        } catch (IllegalAccessException e){
            e.printStackTrace();
            throw new IllegalAccessException("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }

    }

    public LoginUserDTO signupWithoutProfile(SignupDTO signupDTO) throws Exception {
        try {
            System.out.println(signupDTO.getEmail());
            List<User> users = userRepository.findAllByPlatformAndEmail(signupDTO.getSns(), signupDTO.getEmail());
            if (users.size() >= 1) throw new IllegalAccessException();

            User user = User.builder()
                    .nickname(signupDTO.getNickname())
                    .email(signupDTO.getEmail())
                    .platform(signupDTO.getSns())
                    .authority(Authority.ROLE_GUEST)
                    .build();
            userRepository.save(user);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getEmail());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);

            TokenDTO tokenDTO = tokenProvider.generateTokenDTO(authentication);

            LoginUserDTO loginUserDTO = new LoginUserDTO(tokenDTO.getAccessToken(), user);

            return loginUserDTO;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }

}


