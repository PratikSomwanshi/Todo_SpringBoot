package com.wanda.service;



import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.wanda.entity.Users;
import com.wanda.utils.exceptions.CustomException;
import com.wanda.utils.exceptions.response.LoginResponse;
import com.wanda.utils.exceptions.response.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GoogleOAuthService {

    private UserService userService;
    private JWTService jwtService;

    public GoogleOAuthService(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    String CLIENT_ID = "816468624081-blta2oam58n9eahfgb7hdna6i14vi50t.apps.googleusercontent.com";

    public LoginResponse validateGoogleOAuthToken(String googleToken) {


        try {
            // Initialize transport and JSON factory
            var transport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            // Build the verifier
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            // Verify the token
            GoogleIdToken idToken = verifier.verify(googleToken);

            // Check if the token is valid and the email is verified
//            idToken != null ? idToken.getPayload() : null;

            if (idToken == null) {
                throw new CustomException("Invalid Google OAuth token", HttpStatus.NOT_FOUND, "INVALID_GOOGLE_OAUTH_TOKEN");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            var email = payload.getEmail();

            var existingUser = this.userService.getUserByEmail(email);

            String token = this.jwtService.generate(existingUser.getEmail());



            return new LoginResponse(existingUser.getEmail(), existingUser.getUsername(), token);

        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage(), e.getStatusCode(), e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("Invalid Token", HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
        }
    }

}
