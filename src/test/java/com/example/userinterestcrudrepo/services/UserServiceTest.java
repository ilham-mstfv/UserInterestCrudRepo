package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.exceptions.InsertUserException;
import com.example.userinterestcrudrepo.exceptions.NoUsersFoundException;
import com.example.userinterestcrudrepo.mappers.UserMapper;
import com.example.userinterestcrudrepo.models.*;
import com.example.userinterestcrudrepo.models.requests.UserFilterRequest;
import com.example.userinterestcrudrepo.models.requests.UserRequest;
import com.example.userinterestcrudrepo.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserJpaRepository userRepository;

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
                    BigDecimal.valueOf(100.2)));

    static UserRequest staticUserRequest = new UserRequest(
            staticUsers.getFirst().getPersonalData(),
            staticUsers.getFirst().getAge(),
            staticUsers.getFirst().getInterests(),
            staticUsers.getFirst().getCountry(),
            staticUsers.getFirst().getLang(),
            staticUsers.getFirst().getBalance());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация моков
    }

    @Test
    void getAllUsers_ReturnUserList_WhenFound() {
        when(userRepository.getAllUsers())
                .thenReturn(staticUsers);

        assertThat(userService.getAllUsers()).isEqualTo(staticUsers);
    }

    @Test
    void getAllUsers_ReturnUserList_WhenNotFound() {
        when(userRepository.getAllUsers())
                .thenReturn(List.of());

        assertThrows(NoUsersFoundException.class, () ->
                userService.getAllUsers());
    }

    @Test
    void createUserByRequest_ReturnUser_WhenCreated() {
        when(userMapper.userRequestToUser(staticUserRequest))
                .thenReturn(staticUsers.getFirst());

        assertThat(userService.createUserByRequest(staticUserRequest))
            .isEqualTo(staticUsers.getFirst());
    }

    @Test
    void insertUser_ReturnUserId_WhenInserted() {
        assertThat(userService.insertUser(staticUsers.getFirst()))
                .isEqualTo(staticUsers.getFirst().getId());
    }

    @Test
    void insertUser_ThrowsException_WhenInsertionFailed() {
        doThrow(new RuntimeException())
                .when(userRepository).insertUser(any(User.class));

        assertThrows(InsertUserException.class, () ->
                userService.insertUser(staticUsers.getFirst()));
    }

    @Test
    void createAndInsertUserByRequest_ReturnUserId_WhenOk() {
        when(userMapper.userRequestToUser(staticUserRequest))
                .thenReturn(staticUsers.getFirst());

        assertThat(userService.createAndInsertUserByRequest(staticUserRequest))
                .isEqualTo(staticUsers.getFirst().getId());
    }

    @Test
    void getUserById_ReturnUser_WhenFound() {
        when(userRepository.getUserById(staticUsers.getFirst().getId()))
                .thenReturn(Optional.ofNullable(staticUsers.getFirst()));

        assertThat(userService.getUserById(staticUsers.getFirst().getId()))
                .isEqualTo(staticUsers.getFirst());
    }

    @Test
    void getUserById_ReturnUser_WhenNotFound() {
        when(userRepository.getUserById(staticUsers.getFirst().getId()))
                .thenReturn(Optional.empty());

        assertThrows(NoUsersFoundException.class, () ->
                userService.getUserById(staticUsers.getFirst().getId()));
    }

    @Test
    void findUsersByFilter_ReturnUserList_WhenFound() {
        when(userRepository.getUsersByFilter(any(UserFilterRequest.class)))
                .thenReturn(staticUsers);

        assertThat(userService.findUsersByFilter(new UserFilterRequest()))
                .isEqualTo(staticUsers);
    }

    @Test
    void findUsersByFilter_ReturnUserList_WhenNotFound() {
        when(userRepository.getUsersByFilter(any(UserFilterRequest.class)))
                .thenReturn(List.of());

        assertThrows(NoUsersFoundException.class, () ->
                userService.findUsersByFilter(new UserFilterRequest()));
    }
}
