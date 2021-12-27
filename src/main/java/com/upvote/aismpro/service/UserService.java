package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.ArtistDetailDTO;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.Create;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.CreateRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreateRepository createRepository;
    @Autowired
    private CustomModelMapper modelMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ArtistDetailDTO getArtistDetailInfo(Long userID) throws Exception {
        try {
            User user = userRepository.getById(userID);

            List<Create> creates = createRepository.findAllByUser_UserIdOrderBySong_CreateDateDesc(userID);

            List<String> genres = creates.stream().map(song -> song.getSong().getOne()).collect(Collectors.toList());
            List<String> firstKeywords = creates.stream().map(song -> song.getSong().getTwo()).collect(Collectors.toList());
            List<String> secondKeywords = creates.stream().map(song -> song.getSong().getThree()).collect(Collectors.toList());
            List<String> thirdKeywords = creates.stream().map(song -> song.getSong().getFour()).collect(Collectors.toList());
//            List<String> fourthKeywords = creates.stream().map(song -> song.getSong().getFive()).collect(Collectors.toList());
//            List<String> sixthKeywords = creates.stream().map(song -> song.getSong().getSix()).collect(Collectors.toList());

            List<String> keywords = new ArrayList<>(Arrays.asList(getMostFrequentTags(genres, 1), getMostFrequentTags(firstKeywords, 1), getMostFrequentTags(secondKeywords, 1), getMostFrequentTags(thirdKeywords, 1)));

            return new ArtistDetailDTO(user.getUserId(), user.getNickname(), user.getProfile(), keywords);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public String getMostFrequentTags(List<String> genres, int cntLimit) {
        return genres.stream().collect(Collectors.groupingBy(genre -> genre, Collectors.counting())).entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).map(Map.Entry::getKey).limit(cntLimit).reduce("", String::concat);
    }

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
