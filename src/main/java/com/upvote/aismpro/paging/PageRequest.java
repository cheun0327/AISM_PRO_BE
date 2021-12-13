package com.upvote.aismpro.paging;


import org.springframework.data.domain.Sort;

public class PageRequest {

    private int page;
    private int size;
    private Sort.Direction direction;

    public void setPage(int page) {
        // 원래 페이징은 0부터 시작인데 1부터 시작으로 바꿔줌
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        int DEFAULT_SIZE = 6;
        int MAX_SIZE = 20;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }
}
