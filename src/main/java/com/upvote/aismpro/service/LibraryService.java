package com.upvote.aismpro.service;

import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.SongDetail;
import com.upvote.aismpro.repository.SongDetailRepository;
import com.upvote.aismpro.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LibraryService implements LibraryServiceInter{
    @Autowired
    private SongDetailRepository songDetailRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    public List<Song> getSearchResult(LibrarySearchDTO librarySearchDto) {
        //List<String> genreLi = Arrays.asList(librarySearchDto.getGenre().split(","));
        //List<String> mood1Li = Arrays.asList(librarySearchDto.getMood1().split(","));
        //List<String> mood2Li = Arrays.asList(librarySearchDto.getMood2().split(","));

        // list안의 값을 and로 엮어줘야하는데, 개수가 동적이다. 어떻게 잘 처리할 수 있을까?
        // SELECT * FROM aism_pro.song_detail where genre IN ('Rock','Jazz') or mood1 IN ('암울한','잔잔한');
//        List <SongDetail> sd = songDetailRepository.findSongId(
//                librarySearchDto.getType(), librarySearchDto.getLength(), "Pop", "리드미컬한", "신나는");
        System.out.println("songDetailService");
        List<String> songIdList = songDetailRepository.findSongIdBySearchParamQD(librarySearchDto);
        List<Song> songList = songRepository.findAllByIdListQD(songIdList);
        System.out.println(songList);
        return songList;
    }
}


