package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyAlbumRepository extends JpaRepository<User, String> {

    /**
     * 작곡한 음원 or 구매한 음원 가져오는 구현체
     *
     * @param userID 사용자 ID의 ID
     * @param option 음원 종류 : 작곡한 음원, 구매한 음원
     * @return [{songName, fileName, userID, songID, option}, ...]
     */
    @Query("SELECT myAlbum.userId, myAlbum.songId, song.songName, song.fileName, myAlbum.authority " +
            "from MyAlbum myAlbum " +
            "join Song song on myAlbum.songId = song.Id " +
            "where myAlbum.userId = :userID and myAlbum.authority = :option"
    )
    List<Object[]> findByUserIDAndOption(@Param("userID") String userID, @Param("option") String option);

    /**
     * 좋아요한 음원 가져오는 구현체
     *
     * @param userID 사용자 ID의 ID
     * @return [{songName, fileName, userID, songID, option}, ...]
     */
    @Query("SELECT song.creatorID, song.Id, song.songName, song.fileName " +
            "from Favor favor " +
            "join Song song on favor.songId = song.Id " +
            "where favor.userId = :userID"
    )
    List<Object[]> findLikeSongByUserID(@Param("userID") String userID);

    /**
     * 플레이리스트 가져오는 구현체
     *
     * @param userID 사용자 ID의 ID
     * @return [{userID, playlistID, playlistName, playlistImg, playlistState}, ...]
     * playlistState : public | private
     */
    @Query("select playlist.userId, playlist.playlistId, playlist.name, playlist.img,  playlist.state " +
    "from PlayList playlist " +
    "where playlist.userId = :userID"
    )
    List<Object[]> findPlayListByUserID(@Param("userID") String userID);
}
