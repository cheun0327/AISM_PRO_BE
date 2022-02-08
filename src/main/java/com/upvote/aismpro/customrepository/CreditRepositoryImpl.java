package com.upvote.aismpro.customrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.upvote.aismpro.entity.Credit;
import com.upvote.aismpro.entity.QCredit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.QueryCreationException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CreditRepositoryImpl implements CreditRespositoryCustom {
    private final JPAQueryFactory query;
    private final QCredit credit = QCredit.credit1;

}
