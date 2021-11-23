package com.upvote.aismpro.sns;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
public class NaverTokenVerifier {
    private String accessToken;

    @GetMapping("/naver/auth/getAccessToken")
    public String getAccessToken(@RequestParam String access_token) throws IOException {
        accessToken = access_token;

        return (StringUtils.isNotBlank(accessToken)) ? "success" : "fail";
    }

    public String getEmail(JsonObject userInfo) {
        return (String) userInfo.get("email").toString();
    }

    public String getNickname(JsonObject userInfo) {
        return (String) userInfo.get("nickname").toString();
    }

    public Map<String, Object> getUserInfo(String accessToken) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        String apiURL = "https://openapi.naver.com/v1/nid/me";
        String headerStr = "Bearer " + accessToken;

        String res = requestToNaver(apiURL, headerStr);

        JsonParser parser = new JsonParser();
        Object obj = parser.parse( res );
        JsonObject jsonObj = (JsonObject) obj;
        JsonObject response = (JsonObject) jsonObj.get("response");

        String email = getEmail(response);
        String nickname = getNickname(response);

        map.put("email", email);
        map.put("nickname", nickname);

        return map;
    }

    private String requestToNaver(String apiURL, String headerStr) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

        if(headerStr != null && !headerStr.equals("") ) {
            con.setRequestProperty("Authorization", headerStr);
        }

        int responseCode = con.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer res = new StringBuffer();

        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }

        br.close();

        return res.toString();
    }
}