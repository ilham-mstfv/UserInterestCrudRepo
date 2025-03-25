package com.example.userinterestcrudrepo.repository;

import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.models.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserJpaRepository.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    static List<User> staticUsers = List.of(
            new User("John Doe", 20,
                    List.of(new Interest("soccer", "sport", "american football"),
                             new Interest("football", "sport", "some kind of sport"),
                             new Interest("handball", "sport", "some kind of sport")),
                    Country.AZE, Lang.RUS,
                    BigDecimal.valueOf(100.2)),

            new User("Jim Milton", 32,
                   List.of(new Interest("Astronomy", "space", "Studying celestial objects"),
                            new Interest("Culinary Art", "food", "Cooking food"),
                            new Interest("Photography", "photography", "Capturing images")),
                    Country.AZE, Lang.RUS,
                    BigDecimal.valueOf(100.2))
    );

    @BeforeEach
    void setUp() {
        staticUsers.forEach(entityManager::merge);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void getAllUsers_ReturnUserList_WhenFound() {
        List<User> userList = userRepository.getAllUsers();

        assertThat(userList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(staticUsers.size());

        for (int i = 0; i < userList.size(); i++) {
            assertThat(userList.get(i))
                    .isEqualTo(staticUsers.get(i));
        }
    }

    @Test
    void getUserById_ReturnUser_WhenFound() {
        assertThat(userRepository.getUserById(staticUsers.getFirst().getId()))
                .isPresent()
                .get()
                .isEqualTo(staticUsers.getFirst());
    }

    @Test
    void getUserById_ReturnUser_WhenNotFound() {
        assertThat(userRepository.getUserById(-1)).isEmpty();
    }

    @Test
    void insertAndCheckUser_ReturnVoid_WhenInserted() {
        User testUser = new User(
                "Sam Brown", 27,
                List.of(new Interest("Riding horses", "sport", "kind of sport")),
                Country.AZE, Lang.RUS, BigDecimal.valueOf(2430.43)
        );
        userRepository.insertUser(testUser);
        assertThat(userRepository.getUserById(testUser.getId()))
                .isPresent()
                .get()
                .isEqualTo(testUser);
    }

    @Test
    void findByFilter_ReturnUserList_WhenFound() {
        UserFilterRequest filter = new UserFilterRequest();
        filter.setAge(new AgeFilter(1, 150));
        filter.setBalance(new BalanceFilter(
                BigDecimal.valueOf(0.0),
                BigDecimal.valueOf(10_000.0)));

        assertThat(userRepository.getUsersByFilter(filter))
                .isNotNull()
                .isNotEmpty()
                .hasSize(staticUsers.size())
                .isEqualTo(staticUsers);

        filter.setPersonalData(staticUsers.getFirst().getPersonalData());
        filter.setCountry(staticUsers.getFirst().getCountry());
        filter.setLang(staticUsers.getFirst().getLang());

        assertThat(userRepository.getUsersByFilter(filter))
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(staticUsers.getFirst());
    }
}
