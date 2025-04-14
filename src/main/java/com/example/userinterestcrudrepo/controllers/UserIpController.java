package com.example.userinterestcrudrepo.controllers;

import com.example.userinterestcrudrepo.entities.UserIp;
import com.example.userinterestcrudrepo.models.responses.ApiResponse;
import com.example.userinterestcrudrepo.models.requests.UserIpFilterRequest;
import com.example.userinterestcrudrepo.services.UserIpService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/users/auth/filter")
    public ApiResponse<List<UserIp>> getAllUserIpsWithFilter(@Valid @RequestBody UserIpFilterRequest filter) {
        return ApiResponse.success(userIpService.getUserIpListByFilter(filter));
    }
}
