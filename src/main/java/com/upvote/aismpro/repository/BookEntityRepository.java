package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookEntityRepository extends CrudRepository<BookEntity, UUID> {

    Optional<BookEntity> findByName(String name);

    @Query("select distinct book.name from BookEntity book")
    List<String> findBookName();
}