package com.movie.me.utility;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.movie.me.domain.User;

import java.util.Arrays;
import java.util.concurrent.Exchanger;

/**
 * Created by Clarissa on 11/30/16.
 */
public class GoogleUserAuthentication {
    private static String CLIENT_ID = "19080392206-6h0ehbmrlmho466ecvj44d4405rha9jk.apps.googleusercontent.com";
    private static HttpTransport transport;
    private static JsonFactory jsonFactory;

    public static User authenticate(String idTokenString) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Arrays.asList(CLIENT_ID))
                .setIssuer("https://accounts.google.com")
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                if(emailVerified) {
                    String name = (String) payload.get("name");
                    String pictureUrl = (String) payload.get("picture");
                    String age = (String) payload.get("age");

                    User user = new User();
                    user.setEmail(email);
                    user.setPhoto(pictureUrl);
                    user.setUsername(user.getEmail().substring(0, user.getEmail().indexOf("@")));

                    return user;
                }
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
