package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.ArtistDetailDTO;
import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.entity.KeywordPath;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final CustomModelMapper modelMapper;

    public ArtistDetailDTO getArtistDetailInfo(Long userID) throws Exception {
        User user = userRepository.getById(userID);

        List<KeywordPath> keywordPathList = songRepository.findAllByUserIdFetchUserQD(userID).stream()
                .map(Song::getKeywordPath)
                .collect(Collectors.toList());

        List<String> genres = keywordPathList.stream().map(KeywordPath::getGenre).collect(Collectors.toList());
        List<String> firstKeywords = keywordPathList.stream().map(KeywordPath::getCategory).collect(Collectors.toList());
        List<String> secondKeywords = keywordPathList.stream().map(kp -> kp.getSubCategory().getKeyword()).collect(Collectors.toList());
        List<String> thirdKeywords = keywordPathList.stream().map(KeywordPath::getKeyword).collect(Collectors.toList());

        List<String> keywords = Arrays.asList(
                getMostFrequentTags(genres, 1),
                getMostFrequentTags(firstKeywords, 1),
                getMostFrequentTags(secondKeywords, 1),
                getMostFrequentTags(thirdKeywords, 1));

        return new ArtistDetailDTO(user.getUserId(), user.getNickname(), user.getProfile(), keywords);
    }

    public String getMostFrequentTags(List<String> genres, int cntLimit) {
        return genres.stream()
                .collect(Collectors.groupingBy(genre -> genre, Collectors.counting())).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .limit(cntLimit)
                .reduce("", String::concat);
    }

    // email ?????? ??????
    public void emailDoubleCheck(String email) {
        List<User> findUsers = userRepository.findByEmail(email);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("?????? ???????????? ??????????????????.");
        }
    }

    // nickname ?????? ??????
    public void nicknameDoubleCheck(String nickName) throws Exception {
        List<User> findUsers = userRepository.findByNickname(nickName);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("?????? ???????????? ??????????????????.");
        }
    }

    // ????????? ?????? ????????????
    public UserDTO updateUser(Long userId, UserDTO user) throws Exception {
        try {
            final User fetchedUser = userRepository.findById(userId).get();

            if (user.getEmail() != null) fetchedUser.setEmail(user.getEmail());
            if (user.getNickname() != null) fetchedUser.setNickname(user.getNickname());
            userRepository.save(fetchedUser);

            return modelMapper.toUserDTO().map(fetchedUser, UserDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    public UserDTO uploadUserProfile(Long userId, MultipartFile file) throws Exception {
        try {
            User user = userRepository.getById(userId);

            // ????????? ????????? ?????? ?????? ??????
            String[] imgNameArr = file.getOriginalFilename().split("\\.");
            String imgFolder = user.getUserId().toString();
            String imgName = imgFolder + "." + imgNameArr[imgNameArr.length - 1];

            String dirPath = "/Users/upvote3/chaeeun/dev/react-workspace/AISM_PRO_FE/src/components/content/image/user/" + imgFolder;
            // String dirPath = "/var/lib/jenkins/workspace/AISM_PRO_REACT/src/components/content/image/user/" + imgFolder;

            File profileDir = new File(dirPath);

            if (!profileDir.exists()) profileDir.mkdir();
            file.transferTo(new File(dirPath + "/" + imgName));

            user = userRepository.save(user.setProfile(imgFolder + "/" + imgName));

            return modelMapper.toUserDTO().map(user, UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            // ????????? ????????? ???????????? ?????? ?????????
            throw new IOException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
