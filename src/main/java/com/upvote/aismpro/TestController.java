package com.upvote.aismpro;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin
public class TestController {

    @PostMapping("/test")
    @ResponseBody
    public String test (@RequestBody Map<String, Object> map) {
        System.out.println(map);

        return "hi ";
    }
}
