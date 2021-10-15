package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Album;
import com.upvote.aismpro.entity.AlbumPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, AlbumPK> {

}
