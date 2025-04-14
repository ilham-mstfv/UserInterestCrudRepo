package com.example.userinterestcrudrepo.models.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class BalanceFilter {

    private BigDecimal from;

    private BigDecimal to;
}
