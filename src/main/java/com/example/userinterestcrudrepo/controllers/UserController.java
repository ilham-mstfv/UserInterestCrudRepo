package com.example.userinterestcrudrepo.controllers;

import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.models.responses.ApiResponse;
import com.example.userinterestcrudrepo.models.requests.UserFilterRequest;
import com.example.userinterestcrudrepo.models.requests.UserRequest;
import com.example.userinterestcrudrepo.models.responses.UserResponse;
import com.example.userinterestcrudrepo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/all")
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ApiResponse<User> getUserById(@PathVariable("id") int id) {
        return ApiResponse.success(userService.getUserById(id));
    }

    @PostMapping("/users/filter")
    public ApiResponse<List<User>> findUsersByFilter(@Valid @RequestBody UserFilterRequest filter) {
        return ApiResponse.success(userService.findUsersByFilter(filter));
    }

    @PostMapping("/users/add")
    public ApiResponse<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        return ApiResponse.success(new UserResponse(
                userService.createAndInsertUserByRequest(userRequest)));
    }
}

