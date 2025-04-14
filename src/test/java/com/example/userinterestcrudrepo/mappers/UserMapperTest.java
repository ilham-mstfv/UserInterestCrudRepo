package com.example.userinterestcrudrepo.mappers;

import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.models.Country;
import com.example.userinterestcrudrepo.models.Lang;
import com.example.userinterestcrudrepo.models.requests.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    static UserRequest userRequest = new UserRequest(
            "John Doe", 20,
            List.of(new Interest("soccer", "sport", "some kind of sport")),
            Country.AZE, Lang.RUS, BigDecimal.valueOf(100.00)
    );
    static User expectedUser = new User(
            userRequest.getPersonalData(), userRequest.getAge(),
            userRequest.getInterests(),
            userRequest.getCountry(), userRequest.getLang(),
            userRequest.getBalance()
    );

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserByRequest_ReturnUser_WhenMapped() {
        assertThat(userMapper.userRequestToUser(userRequest))
                .isEqualTo(expectedUser);
    }
}
