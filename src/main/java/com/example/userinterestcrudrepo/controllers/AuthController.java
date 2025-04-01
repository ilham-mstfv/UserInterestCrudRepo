package com.example.userinterestcrudrepo.controllers;

import com.example.userinterestcrudrepo.models.ApiResponse;
import com.example.userinterestcrudrepo.models.AuthRequest;
import com.example.userinterestcrudrepo.models.AuthResponse;
import com.example.userinterestcrudrepo.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/users/auth/log")
    public ApiResponse<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        return ApiResponse.success(new AuthResponse(
                authService.logByRequest(authRequest)));
    }

    @GetMapping("/users/auth/log")
    public ApiResponse<String> message() {
        return ApiResponse.success("AuthController Message");
    }

    @PostMapping
    public ApiResponse<AuthResponse> createUser(@Valid @RequestBody AuthRequest authRequest) {
        // return ApiResponse.success(authService.registerByRequest(authRequest));

        return ApiResponse.success(new AuthResponse());
    }
}
