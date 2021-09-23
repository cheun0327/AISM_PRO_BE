package com.upvote.aismpro.loginverifier;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Service
// 발급받은 token의 무결성 확인
// Google을 통해 발행된 것이 맞는지 확인
public class GoogleTokenVerifier {

    private final NetHttpTransport transport = new NetHttpTransport();
    private final JsonFactory jsonFactory = new GsonFactory();

    public int tokenVerify(String tokenId) {

        System.out.println("idToken : " + "882280880458-ok1aj04ukbpc4hj5aidvohf09vrqndvm.apps.googleusercontent.com");

        GoogleIdTokenVerifier gitVerifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
                .setAudience(Collections.singletonList("882280880458-ok1aj04ukbpc4hj5aidvohf09vrqndvm.apps.googleusercontent.com"))
                .build();

        GoogleIdToken git = null;

        try {
            git = gitVerifier.verify(tokenId);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (git == null) {
            System.out.println("Google ID Token is invalid");
        } else {
            GoogleIdToken.Payload payload = git.getPayload();

            // Print user identifier & Get profile information from payload
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            //String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            //String familyName = (String) payload.get("family_name");
            //String givenName = (String) payload.get("given_name");

            System.out.println("email: " + email);
            System.out.println("emailVerify: " + emailVerified);
            System.out.println("name: " + name);
            System.out.println("locale: " + locale);


        }
        return 0;
    }
}