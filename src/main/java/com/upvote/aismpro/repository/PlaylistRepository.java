package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.PlaylistRepositoryCustom;
import com.upvote.aismpro.dto.PlaylistInfoDTO;
import com.upvote.aismpro.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PlaylistRepository extends JpaRepository<PlayList, String>, PlaylistRepositoryCustom {
//    // Playlist 정보 가져오기
//    @Query("SELECT new com.upvote.aismpro.dto.PlaylistInfoDTO(" +
//            "user.nickName as playlistCreatorName, playlist.name as playlistName, playlist.state, " +
//            "playlist.img, song.createDate, song.creatorID as songCreatorID, song.songName, song.fileName) " +
//            "FROM PlayListSong playlistSong " +
//            "INNER JOIN PlayList playlist on playlist.playlistId = playlistSong.playlistId " +
//            "INNER JOIN Song song on playlistSong.songId = song.Id " +
//            "INNER JOIN User user on playlist.userId = user.id " +
//            "where playlist.playlistId = :ID"
//    )
//    List<PlaylistInfoDTO> findInfoByCategoryAndPlaylistId(@Param("ID") String ID);
}
