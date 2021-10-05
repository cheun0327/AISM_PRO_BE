package com.upvote.aismpro.repository;

import com.upvote.aismpro.customrepository.SongDetailRepositoryCustom;
import com.upvote.aismpro.entity.SongDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongDetailRepository extends JpaRepository<SongDetail, String>, SongDetailRepositoryCustom {

}
