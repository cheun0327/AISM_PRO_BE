package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.MyAlbum;
import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyAlbumRepository extends JpaRepository<User, String> {

    @Query("SELECT myAlbum.userId, myAlbum.songId, song.songName, song.fileName, myAlbum.authority " +
            "from MyAlbum myAlbum " +
            "join Song song on myAlbum.songId = song.Id " +
            "where myAlbum.userId = :userID and myAlbum.authority = :option"
    )
    List<Object> findByUserID(@Param("userID") String userID, @Param("option") String option);
}
