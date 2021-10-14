package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.MyAlbum;
import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyAlbumRepository extends JpaRepository<MyAlbum, String> {

//    // 내가 작곡한 음원 | 구매한 음원 가져오기
//    @Query("SELECT myAlbum.userId, myAlbum.songId, song.songName, song.fileName, myAlbum.authority " +
//            "from MyAlbum myAlbum " +
//            "join Song song on myAlbum.songId = song.songId " +
//            "where myAlbum.userId = :userID and myAlbum.authority = :option"
//    )
//    List<Object[]> findByUserIDAndOption(@Param("userID") String userID, @Param("option") String option);
//
//     좋아요 리스트 가져오기
//    @Query("SELECT song.creatorID, song.songId, song.songName, song.fileName " +
//            "from Favor favor " +
//            "join Song song on favor.songId = song.songId " +
//            "where favor.userId = :userID"
//    )
//    List<Object[]> findLikeSongByUserID(@Param("userID") String userID);
//
//     플레이 리스트 가져오기
//    @Query("select playlist.userId, playlist.playlistId, playlist.name, playlist.img,  playlist.state " +
//    "from PlayList playlist " +
//    "where playlist.userId = :userID"
//    )
//    List<Object[]> findPlayListByUserID(@Param("userID") String userID);
}
