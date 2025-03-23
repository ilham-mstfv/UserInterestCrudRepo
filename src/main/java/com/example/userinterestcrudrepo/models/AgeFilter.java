package com.example.userinterestcrudrepo.models;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AgeFilter {

    @Positive
    private int from;

    @Positive
    private int to;
}
