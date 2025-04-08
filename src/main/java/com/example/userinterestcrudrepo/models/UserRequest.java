package com.example.userinterestcrudrepo.models;

import com.example.userinterestcrudrepo.entities.Interest;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Personal data must not be blank")
    @Size(max=64)
    private String personalData;

    @NotNull
    @Min(1)
    @Max(100)
    private int age;

    @NotNull
    private List<String> interests;

    @NotNull
    private Country country;

    @NotNull
    private Lang lang;

    @NotNull
    private BigDecimal balance;
}
