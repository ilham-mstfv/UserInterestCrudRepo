package com.example.userinterestcrudrepo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class UserFilterRequest {

    private String personalData;

    private AgeFilter age;

    private Country country;

    private Lang lang;

    private BalanceFilter balance;
}
