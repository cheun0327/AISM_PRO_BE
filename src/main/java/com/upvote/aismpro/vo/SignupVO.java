package com.upvote.aismpro.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;

import java.util.LinkedHashMap;

@Getter
@NoArgsConstructor
public class SignupVO {
    private String sns;
    private LinkedHashMap<String, Object> info;
}
