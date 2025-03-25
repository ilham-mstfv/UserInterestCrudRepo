package com.example.userinterestcrudrepo.models;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterRequest {

    private String personalData;

    private AgeFilter age;

    private Country country;

    private Lang lang;

    private BalanceFilter balance;
}
