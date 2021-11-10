package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LoginUserDTO;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.OAuth;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.OAuthRepository;
import com.upvote.aismpro.repository.UserRepository;
import com.upvote.aismpro.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class SignupService implements SignupServiceInter{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthRepository oAuthRepository;

    @Autowired
    private SecurityService securityService;

    @Override
    @Transactional
    public LoginUserDTO signup(HttpSession session, String nickName, MultipartFile file) throws Exception {
        try {
            List<User> users = userRepository.findAllByPlatformAndEmail(session.getAttribute("platform").toString(), session.getAttribute("email").toString());
            if (users.size() >= 1) throw new IllegalAccessException();

            String[] imgNameArr = file.getOriginalFilename().split("\\.");
            String newId = UUID.randomUUID().toString();
            String imgFolder = newId.replaceAll("-", "");
            String imgName = imgFolder + "." + imgNameArr[imgNameArr.length - 1];

//            String dirPath = "/Users/upvote3/chaeeun/dev/react-workspace/AISM_PRO_FE/src/components/content/image/user/" + imgFolder;
            String dirPath = "/var/lib/jenkins/workspace/AISM_PRO_REACT/src/components/content/image/user/" + imgFolder;

            File profileDir = new File(dirPath);

            if (!profileDir.exists()) {
                profileDir.mkdir();
            }
            file.transferTo(new File(dirPath + "/" + imgName));

            User user = new User(
                    newId,
                    nickName,
                    session.getAttribute("email").toString(),
                    session.getAttribute("platform").toString(),
                    imgFolder + "/" + imgName
            );

            userRepository.save(user);

            //user token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));

            LoginUserDTO loginUser = new LoginUserDTO(token, user);

            return loginUser;
        } catch (IllegalAccessException e){
            e.printStackTrace();
            throw new IllegalAccessException("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }

    @Override
    @Transactional
    public LoginUserDTO signupWithoutProfile (HttpSession session, String nickName) throws Exception {
        try {
            List<User> users = userRepository.findAllByPlatformAndEmail(session.getAttribute("platform").toString(), session.getAttribute("email").toString());
            if (users.size() >= 1) throw new IllegalAccessException();

            User user = new User(
                    UUID.randomUUID().toString(),
                    nickName,
                    session.getAttribute("email").toString(),
                    session.getAttribute("platform").toString()
            );

            userRepository.save(user);

            //user token 생성
            String token = securityService.createToken(securityService.transformUserToJwtRequestDto(user));

            LoginUserDTO loginUser = new LoginUserDTO(token, user);

            return loginUser;
        } catch (IllegalAccessException e){
            e.printStackTrace();
            throw new IllegalAccessException("이미 등록된 사용자입니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }

    @Transactional
    public void signup1(User user) throws Exception{
        try {
            List<User> users = userRepository.findAllByPlatformAndEmail(user.getPlatform(), user.getEmail());
            System.out.println(users);
            if (users.size() >= 1)  throw new IllegalAccessException();
            userRepository.save(user);

        } catch (IllegalAccessException e){
            throw new IllegalAccessException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("새로운 User 등록에 실패하였습니다.");
        }
    }

    @Override
    public void linking(String userId, String platform, String email) throws Exception{
        try {
            OAuth oAuth = new OAuth();
            oAuth.setUserId(userId);
            oAuth.setPlatform(platform);
            oAuth.setEmail(email);
            oAuthRepository.save(oAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("oAuth 등록에 실패하였습니다.");
        }
    }

    @Override
    public void emailDoubleCheck(String email) {
        List<User> findUsers = userRepository.findByEmail(email);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    public void nickDoubleCheck(String nickName) throws Exception {
        List<User> findUsers = userRepository.findByNickName(nickName);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    private String createRandomId() {
        return UUID.randomUUID().toString();
    }

}
