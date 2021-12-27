package com.upvote.aismpro.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

@Getter
@NoArgsConstructor
public class SignupVO {
    private String sns;
    private LinkedHashMap<String, Object> info;
    private String nickname;
    private MultipartFile profileImg;

    public void print() {
        System.out.println(sns);
        System.out.println(info);
        System.out.println(nickname);
    }
}
