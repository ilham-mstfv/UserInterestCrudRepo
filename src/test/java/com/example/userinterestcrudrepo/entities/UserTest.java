package com.example.userinterestcrudrepo.entities;

import com.example.userinterestcrudrepo.models.Country;
import com.example.userinterestcrudrepo.models.Lang;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {

    static int userId = 1;
    static String userPersonalData = "John Doe";
    static int userAge = 20;
    static List<Interest> userInterests = List.of(
            new Interest(1, "soccer", "sport", "american football"),
            new Interest(2, "football", "sport", "some kind of sport"),
            new Interest(3, "handball", "sport", "some kind of sport")
    );
    static Country userCountry = Country.AZE;
    static Lang userLang = Lang.RUS;
    static BigDecimal userBalance = BigDecimal.valueOf(100.2);


    @Test
    void createUserTest() {
        User user = new User(
                userId, userPersonalData, userAge,
                userInterests,
                userCountry, userLang, userBalance
        );

        assertThat(user.getId()).isNotNull().isEqualTo(userId);
        assertThat(user.getPersonalData()).isNotNull().isEqualTo(userPersonalData);
        assertThat(user.getAge()).isNotNull().isEqualTo(userAge);
        assertThat(user.getInterests()).isNotNull().isEqualTo(userInterests);
        assertThat(user.getCountry()).isEqualTo(userCountry);
        assertThat(user.getLang()).isEqualTo(userLang);
        assertThat(user.getBalance()).isEqualTo(userBalance);
    }

    @Test
    void createUserWithSettersTest() {
        User user = new User();

        user.setId(userId);
        user.setPersonalData(userPersonalData);
        user.setAge(userAge);
        user.setInterests(userInterests);
        user.setCountry(userCountry);
        user.setLang(userLang);
        user.setBalance(userBalance);

        assertThat(user.getId()).isNotNull().isEqualTo(userId);
        assertThat(user.getPersonalData()).isNotNull().isEqualTo(userPersonalData);
        assertThat(user.getAge()).isNotNull().isEqualTo(userAge);
        assertThat(user.getInterests()).isNotNull().isEqualTo(userInterests);
        assertThat(user.getCountry()).isEqualTo(userCountry);
        assertThat(user.getLang()).isEqualTo(userLang);
        assertThat(user.getBalance()).isEqualTo(userBalance);
    }
}
