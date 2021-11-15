package com.upvote.aismpro.customrepository.compose;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThreeFourRepositoryCustom {
    public List<String> findFourByThreeQD(String three);
    public List<String> findFourQD();

}
