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

    @Query("SELECT myAlbum from MyAlbum myAlbum where myAlbum.userId = :userID")
    List<MyAlbum> findByUserID(@Param("userID") String userID);
}
