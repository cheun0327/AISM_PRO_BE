package com.upvote.aismpro.controller;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@JsonComponent
public class PageController  {

    private Object MapUtils;

    @RequestMapping(value = "/hello", method = {RequestMethod.POST})
    @ResponseBody
    public String hello(@RequestBody Map<String, Object> map) {
        if (map == null || map.isEmpty()) System.out.println("empty");
        else System.out.println("not null");

        System.out.println(map);

        return "Hi 현재 서버 시간 : " + new Date() + "입니다. \n";
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }
}
