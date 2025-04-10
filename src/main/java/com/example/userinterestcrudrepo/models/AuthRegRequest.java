package com.example.userinterestcrudrepo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegRequest {

    @NotBlank
    @Size(max = 64)
    private String username;

    @Size(min = 8, max = 64)
    @NotBlank
    private String password;

    private UserRole role;
}