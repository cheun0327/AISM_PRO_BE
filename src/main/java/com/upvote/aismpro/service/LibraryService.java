package com.upvote.aismpro.service;

import com.upvote.aismpro.custommodelmapper.CustomModelMapper;
import com.upvote.aismpro.dto.LibrarySearchDTO;
import com.upvote.aismpro.dto.PlaylistDTO;
import com.upvote.aismpro.dto.PlaylistInfoDTO;
import com.upvote.aismpro.dto.SongBarDTO;
import com.upvote.aismpro.entity.PlayList;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.SongDetail;
import com.upvote.aismpro.entity.User;
import com.upvote.aismpro.repository.PlaylistRepository;
import com.upvote.aismpro.repository.SongDetailRepository;
import com.upvote.aismpro.repository.SongRepository;
import com.upvote.aismpro.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibraryService implements LibraryServiceInter{
    @Autowired
    private SongDetailRepository songDetailRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomModelMapper modelMapper;

    @Override
    public List<PlayList> getPlaylistDto() {
        List<PlayList> rawpl = playlistRepository.findAll();

        for (PlayList pl : rawpl) {
            PlaylistDTO pldto = modelMapper.playlistMapper().map(pl, PlaylistDTO.class);
            pldto.print();
        }

        return rawpl;
    }

    @Override
    // 라이브러리 페이지 검색 결과 반환
    public Map<String, Object> getSearchResult(LibrarySearchDTO librarySearchDto) {

        Map<String, Object> map = new HashMap<>();

        // 만약 "song" type 포함이면 플레이리스트도 가져옴
        List<PlayList> playlistList = getPlaylistList(librarySearchDto);
        System.out.println("\n\nplaylist done\n\n");
        map.put("playlist", playlistList);

        // 검색 입력 값으로 곡 상세 정보 가져오기
        List<SongDetail> songDetailList = songDetailRepository.findSongDetailBySearchParamQD(librarySearchDto);
        System.out.println("\n\nsongDetail done\n\n");
        // songId로 기본 곡 정보 가져오기
        //List<Song> songList = songRepository.findSongByIdListQD(songDetailList.stream().map(songDetail -> songDetail.getSongId()).collect(Collectors.toList()));
        // 기본 곡 정보로 SongBarDTO 생성
        List<SongBarDTO> songBarList = getSongBarList(songDetailList);
        // 검색 키워드 필터링
        if (!Objects.equals(librarySearchDto.getSearch(), "") && librarySearchDto.getSearch() != null) {
            map.put("song", filterSearchKeyword(librarySearchDto.getSearch(), songBarList));
        }
        else map.put("song", songBarList);



        return map;
    }

    // 기본 곡 정보와 상세 곡 정보로 SongBarDTO 가공
    List<SongBarDTO> getSongBarList(List<SongDetail> songDetailList) {
        List<SongBarDTO> songBarList = new ArrayList<>();

        Integer cnt = 0;
        for (SongDetail sd : songDetailList) {
            SongBarDTO tmp = new SongBarDTO();

            Song s = sd.getSong();

            tmp.setSongId(s.getSongId());
            tmp.setSongName(s.getSongName());


            if (s.getUser() != null)    tmp.setCreator(s.getUser().getNickName());
            else                        tmp.setCreator("작곡가 없음");

            tmp.setFileName("sample0" + String.valueOf(cnt % 5+1) + ".wav");
            tmp.setThumbnail("sample0" + String.valueOf(cnt % 5+1) +".png");

            List<String> tag = new ArrayList<>(Arrays.asList(new String[]{sd.getMood1(), sd.getMood2(), sd.getMood3()}));
            tmp.setTag(tag);

            songBarList.add(tmp);
            cnt ++;
        }
        return songBarList;
    }

    // 검색으로 받은 DTO로 플레이리스트 정보 가져옴
    List<PlayList> getPlaylistList(LibrarySearchDTO librarySearchDto) {
        // 여기서 검색 keyword filtering
        List<PlayList> playlistList = new ArrayList<>();

        if (librarySearchDto.getType().contains("song")){
            playlistList = playlistRepository.findAll();
            for(PlayList pl : playlistList) {
                System.out.println(pl.getUser().getNickName());
            }
        }

        return playlistList;
    }

    // 검색 키워드 필터링
    List<SongBarDTO> filterSearchKeyword(String keyword, List<SongBarDTO> songBarList) {
        List<SongBarDTO> filtered = new ArrayList<>();

        for (SongBarDTO songBar : songBarList) {
            String[] arr = {songBar.getSongName(), songBar.getCreator(), songBar.getSongId()};
            if (Arrays.stream(arr).anyMatch(keyword::equals)) {
                filtered.add(songBar);
                continue;
            }
            if (songBar.getTag().contains(keyword)) filtered.add(songBar);
        }
        return filtered;
    }

    @Override
    // View Detail Playlist
    public List<PlaylistInfoDTO> getPlaylistInfo(String category, String id) {
        List<PlaylistInfoDTO> playlistInfoDTO_li = new ArrayList<>();

        PlayList playlist = playlistRepository.getById(id);

        for (Song plSong : playlist.getSongs()) {
            PlaylistInfoDTO playlistInfoDTO = new PlaylistInfoDTO(
                    playlist.getUser().getNickName(), playlist.getName(),
                    playlist.getState(), playlist.getImg(),
                    plSong.getCreateDate(), "작곡가 아이디",
                    plSong.getSongName(), plSong.getFileName()
            );
            playlistInfoDTO.setSongCreatorName("작곡가 이름");
            playlistInfoDTO_li.add(playlistInfoDTO);
        }
        return playlistInfoDTO_li;
    }
}