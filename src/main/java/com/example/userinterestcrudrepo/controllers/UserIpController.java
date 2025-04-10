package com.example.userinterestcrudrepo.controllers;

import com.example.userinterestcrudrepo.entities.UserIp;
import com.example.userinterestcrudrepo.models.ApiResponse;
import com.example.userinterestcrudrepo.services.UserIpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserIpController {

    private final UserIpService userIpService;

    public UserIpController(UserIpService userIpService) {
        this.userIpService = userIpService;
    }

    @GetMapping("/users/auth/all")
    public ApiResponse<List<UserIp>> getAllUserIps() {
        return ApiResponse.success(userIpService.getAll());
    }
}
