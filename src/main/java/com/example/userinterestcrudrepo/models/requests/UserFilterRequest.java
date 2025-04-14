package com.example.userinterestcrudrepo.models.requests;

import com.example.userinterestcrudrepo.models.Country;
import com.example.userinterestcrudrepo.models.Lang;
import com.example.userinterestcrudrepo.models.filters.AgeFilter;
import com.example.userinterestcrudrepo.models.filters.BalanceFilter;
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
