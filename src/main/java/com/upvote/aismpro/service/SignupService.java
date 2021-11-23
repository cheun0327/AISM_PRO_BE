package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    public LoginUserDTO signup(HttpSession session, String nickname, MultipartFile file) throws Exception {
        try {
            String platform = session.getAttribute("platform").toString();
            String email = session.getAttribute("email").toString();

            List<User> users = userRepository.findAllByPlatformAndEmail(platform, email);
            if (users.size() >= 1) throw new IllegalAccessException();

            // 유저 저장
            User savedUser = userRepository.save(new User(nickname, email, platform));
            System.out.println(savedUser.getUserId());

            // 프로필 이미지 파일 이름 세팅
            String[] imgNameArr = file.getOriginalFilename().split("\\.");
            String imgFolder = savedUser.getUserId().toString();
            String imgName = imgFolder + "." + imgNameArr[imgNameArr.length - 1];

            String dirPath = "/Users/upvote3/chaeeun/dev/react-workspace/AISM_PRO_FE/src/components/content/image/user/" + imgFolder;
            // String dirPath = "/var/lib/jenkins/workspace/AISM_PRO_REACT/src/components/content/image/user/" + imgFolder;

            File profileDir = new File(dirPath);

            if (!profileDir.exists())   profileDir.mkdir();
            file.transferTo(new File(dirPath + "/" + imgName));

            savedUser = userRepository.save(savedUser.setProfile(imgFolder + "/" + imgName));

            //user token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(savedUser));

            LoginUserDTO loginUser = new LoginUserDTO(token, savedUser);

            return loginUser;

        } catch (IllegalAccessException e){
            e.printStackTrace();
            throw new IllegalAccessException("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }

    }

    public LoginUserDTO signupWithoutProfile(HttpSession session, String nickname) throws Exception {
        try {
            String platform = session.getAttribute("platform").toString();
            String email = session.getAttribute("email").toString();

            List<User> users = userRepository.findAllByPlatformAndEmail(platform, email);
            if (users.size() >= 1) throw new IllegalAccessException();

            User user = new User(nickname, email, platform);

            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));

            LoginUserDTO loginUserDTO = new LoginUserDTO(token, user);

            return loginUserDTO;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalAccessException("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }


    public void emailDoubleCheck(String email) {
        List<User> findUsers = userRepository.findByEmail(email);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }


    public void nickDoubleCheck(String nickName) throws Exception {
        List<User> findUsers = userRepository.findByNickname(nickName);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

}
