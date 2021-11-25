package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    // email 중복 체크
    public void emailDoubleCheck(String email) {
        List<User> findUsers = userRepository.findByEmail(email);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    // nickname 중복 체크
    public void nicknameDoubleCheck(String nickName) throws Exception {
        List<User> findUsers = userRepository.findByNickname(nickName);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    // 사용자 정보 업데이트
    public UserDTO updateUser(Long userId, UserDTO user) throws Exception {
        try {
            final User fetchedUser = userRepository.findById(userId).get();

            if (user.getEmail() != null)     fetchedUser.setEmail(user.getEmail());
            if (user.getNickname() != null)  fetchedUser.setNickname(user.getNickname());
            userRepository.save(fetchedUser);

            return modelMapper.toUserDTO().map(fetchedUser, UserDTO.class);
        } catch(Exception e){
            e.printStackTrace();
            throw new Exception();
        }
    }

    public UserDTO uploadUserProfile(Long userId, MultipartFile file) throws Exception {
        try {
            User user = userRepository.getById(userId);

            // 프로필 이미지 파일 이름 세팅
            String[] imgNameArr = file.getOriginalFilename().split("\\.");
            String imgFolder = user.getUserId().toString();
            String imgName = imgFolder + "." + imgNameArr[imgNameArr.length - 1];

            String dirPath = "/Users/upvote3/chaeeun/dev/react-workspace/AISM_PRO_FE/src/components/content/image/user/" + imgFolder;
            // String dirPath = "/var/lib/jenkins/workspace/AISM_PRO_REACT/src/components/content/image/user/" + imgFolder;

            File profileDir = new File(dirPath);

            if (!profileDir.exists())   profileDir.mkdir();
            file.transferTo(new File(dirPath + "/" + imgName));

            user = userRepository.save(user.setProfile(imgFolder + "/" + imgName));

            return modelMapper.toUserDTO().map(user, UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 업로드 안되었을 시에 후처리
            throw new IOException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
