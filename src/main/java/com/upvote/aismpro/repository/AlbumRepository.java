package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.AlbumRepositoryCustom;
import com.upvote.aismpro.dto.AlbumDTO;
import com.upvote.aismpro.entity.Album;
import com.upvote.aismpro.entity.AlbumPK;
import com.upvote.aismpro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, AlbumPK>, AlbumRepositoryCustom {
    List<Album> findAll();
}
