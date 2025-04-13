package com.example.userinterestcrudrepo.controllers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.userinterestcrudrepo.models.ApiResponse;
import com.example.userinterestcrudrepo.models.AuthLogRequest;
import com.example.userinterestcrudrepo.models.AuthRegRequest;
import com.example.userinterestcrudrepo.models.AuthResponse;
import com.example.userinterestcrudrepo.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse<AuthResponse> loginUser(
            @Valid @RequestBody AuthLogRequest authRequest,
            HttpServletRequest httpServletRequest
    ) throws JWTVerificationException {
        return ApiResponse.success(new AuthResponse(
                authService.loginAndSaveUserDataByRequest(authRequest, httpServletRequest)));
    }

    @PostMapping("/users/auth/reg")
    public ApiResponse<String> registerUser(@Valid @RequestBody AuthRegRequest authRequest) {
        return ApiResponse.success(authService.registerByRequest(authRequest));
    }
}
