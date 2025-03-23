package com.example.userinterestcrudrepo.entities;

import com.example.userinterestcrudrepo.models.Country;
import com.example.userinterestcrudrepo.models.Lang;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    @Test
    void createUserTest() {
        User user = new User(
                1,
                "John Doe", 20,
                List.of(
                        new Interest(1, "soccer", "sport", "american football")
                ),
                Country.AZE,
                Lang.RUS,
                BigDecimal.valueOf(100.2)
        );
        assertThat(user.getId()).isNotNull().isEqualTo(1);
        assertThat(user.getPersonalData()).isNotNull().isEqualTo("John Doe");
    }
}
