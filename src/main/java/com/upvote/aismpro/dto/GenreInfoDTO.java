package com.upvote.aismpro.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenreInfoDTO {
    private String genre;
    private Integer categoryCnt;
    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    private String six;

    public GenreInfoDTO(String genre, String one, String two, String three, String four, String five, String six) {
        this.genre = genre;
        this.one = one;
        this.two = two;
        this. three = three;
        this. four = four;
        this.five = five;
        this.six = six;
        String cateArr[] = {one, two, three, four, five, six};
        Integer cnt = 0;
        for (String cate : cateArr) if (cate != null)   cnt++;
        this.categoryCnt = cnt;
    }
}
