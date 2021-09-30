package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.SongDetailRepositoryCustom;
import com.upvote.aismpro.customrepository.SongDetailRepositoryImpl;
import com.upvote.aismpro.entity.Song;
import com.upvote.aismpro.entity.SongDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongDetailRepository extends JpaRepository<SongDetail, String>, SongDetailRepositoryCustom {

}
