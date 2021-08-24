package com.upvote.aismpro.repository;

import com.upvote.aismpro.entity.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookEntityRepository extends CrudRepository<BookEntity, UUID> {

    Optional<BookEntity> findByName(String name);

    @Query("select distinct book.name from BookEntity book")
    List<String> findBookName();
}