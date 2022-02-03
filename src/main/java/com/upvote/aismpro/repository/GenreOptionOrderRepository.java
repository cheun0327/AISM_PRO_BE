package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.GenreOptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreOptionOrderRepository extends JpaRepository<GenreOptionOrder, Long> {

    @Query("SELECT goo " +
            "FROM GenreOptionOrder goo " +
            "INNER JOIN fetch goo.genre " +
            "INNER JOIN fetch goo.genreOption " +
            "WHERE goo.genre.genreName = :genreName")
    List<GenreOptionOrder> findAllByGenreName(@Param("genreName") String genreName);
}
