package com.upvote.aismpro.customrepository.compose;


import com.querydsl.core.Tuple;

import java.util.List;

public interface OneTwoRepositoryCustom {

    public List<String> findOneQD();

    public List<String> findTwoQD();

    public List<String> findTwoByOneQD(String one);

}
