package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, String> {
}
