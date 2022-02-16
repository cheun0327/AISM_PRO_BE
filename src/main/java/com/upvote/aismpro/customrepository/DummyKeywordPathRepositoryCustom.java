package com.upvote.aismpro.customrepository;

import java.util.List;

public interface DummyKeywordPathRepositoryCustom {

    // --- non impl genres methods ---
    List<String> find1stDummyOptions();

    List<String> find2ndDummyOptions(String mood1);

    List<String> find3rdDummyOptions(String mood1, String mood2);
}
