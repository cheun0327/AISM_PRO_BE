package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {
}
