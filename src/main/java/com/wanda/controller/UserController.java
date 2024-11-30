package com.wanda.controller;

import com.wanda.entity.Users;
import com.wanda.service.UserService;
import com.wanda.utils.exceptions.response.SuccessResponse;
import com.wanda.utils.exceptions.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<SuccessResponse<TokenResponse>> login(@RequestBody Users user){
        String token = this.userService.verify(user);

        TokenResponse tokenResponse = new TokenResponse(token);

        SuccessResponse<TokenResponse> success = new SuccessResponse<>(
                true,
                "Successfully generated the token",
                tokenResponse
        );

        return ResponseEntity.ok(success);
    }
}
