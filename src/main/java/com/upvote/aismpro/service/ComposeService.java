package com.upvote.aismpro.service;

import com.upvote.aismpro.repository.ComposeRepository;
import com.upvote.aismpro.repository.SongDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ComposeService implements ComposeServiceInter {

    @Autowired
    private ComposeRepository composeRepository;

    // 키워드 가져오기
    @Override
    public List<String> getKeywords(String keyword) {
        if (keyword.equals("Genre")) {
            List<String> tmpKwd = composeRepository.findKeyword(keyword);
            return Stream.of(tmpKwd.subList(8, 9), tmpKwd.subList(0, 8), tmpKwd.subList(9, 12))
                    .flatMap(x -> x.stream())
                    .collect(Collectors.toList());
        }
        return composeRepository.findKeyword(keyword);
    }

    // 장르 -> 첫번째 분위기 가져오기
    @Override
    public List<String> getFirstMoodByGenre(String genre) {
        return composeRepository.findFirstMoodByGenre(genre);
    }

    // 장르 & 첫번째 분위기 -> 두번째 분위기 가져오기
    @Override
    public List<String> getSecondMoodByFirstMood(String genre, String firstMood) {
        return composeRepository.findSecondMoodByFirstMood(genre, firstMood);
    }
    // 장르 & 첫번째 분위기 & 두번째 분위기 -> 샘플 사운드 가져오기
    @Override
    public String[] getSampleSoundByKeywords(String genre, String firstMood, String secondMood) {
        return composeRepository.findSampleSoundByKeywords(genre, firstMood, secondMood).split(",");
    }
}
