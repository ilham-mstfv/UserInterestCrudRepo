package com.example.userinterestcrudrepo.models.filters;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
