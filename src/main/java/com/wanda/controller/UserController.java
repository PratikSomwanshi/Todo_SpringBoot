package com.wanda.controller;

import com.wanda.dto.AccessToken;
import com.wanda.entity.Users;
import com.wanda.service.GoogleOAuthService;
import com.wanda.service.UserService;
import com.wanda.utils.exceptions.CustomException;
import com.wanda.utils.exceptions.response.LoginResponse;
import com.wanda.utils.exceptions.response.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;
    private GoogleOAuthService googleOAuthService;

    public UserController(UserService userService, GoogleOAuthService googleOAuthService) {
        this.userService = userService;
        this.googleOAuthService = googleOAuthService;
    }


    @PostMapping("/user")
    public ResponseEntity<?> getUser(@RequestBody Users user) {

        try {
            var existingUser = userService.getUserByEmail(user.getEmail());



            var success = new SuccessResponse<>(
                    true,
                    "Successfully saved the user",
                    existingUser
            );

            return ResponseEntity.ok(success);
        }catch(Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.NOT_FOUND, "EMAIL_NOT_EXIST");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<Users>> register(@RequestBody Users user){
        Users savedUser = this.userService.saveUser(user);

        var success = new SuccessResponse<Users>(
                true,
                "Successfully saved the user",
                savedUser
        );

        return ResponseEntity.ok(success);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(@RequestBody Users user){
        LoginResponse loginResponse = this.userService.verify(user);

        SuccessResponse<LoginResponse> success = new SuccessResponse<>(
                true,
                "Successfully generated the token",
                loginResponse
        );

        return ResponseEntity.ok(success);
    }


    @PostMapping("/google/login")
    public ResponseEntity<SuccessResponse<?>> googleLogin(@RequestBody AccessToken accessToken){

        var b = this.googleOAuthService.validateGoogleOAuthToken(accessToken.getAccessToken());


        SuccessResponse<?> success = new SuccessResponse<>(
                true,
                "Successfully generated the token",
                b
        );

        return ResponseEntity.ok(success);
    }
}
