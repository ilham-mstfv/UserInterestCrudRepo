package com.example.userinterestcrudrepo.controllers;

import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.models.requests.InterestRequest;
import com.example.userinterestcrudrepo.models.responses.ApiResponse;
import com.example.userinterestcrudrepo.models.responses.InterestResponse;
import com.example.userinterestcrudrepo.services.InterestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InterestController {

    private final InterestService interestService;

    @Autowired
    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @PostMapping("/users/interest/add")
    public ApiResponse<InterestResponse> addInterest(@Valid @RequestBody InterestRequest interestRequest) {
        return ApiResponse.success(new InterestResponse(
                interestService.createAndInsertInterestByRequest(interestRequest)));
    }

    @GetMapping("/users/interest/all")
    public ApiResponse<List<Interest>> getAllInterests() {
        return ApiResponse.success(interestService.getAllInterests());
    }
}

