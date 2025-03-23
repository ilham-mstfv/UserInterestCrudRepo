package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.entities.UserTest;
import com.example.userinterestcrudrepo.mappers.UserMapper;
import com.example.userinterestcrudrepo.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserJpaRepository userRepository;

    @InjectMocks
    UserMapper userMapper;

    @Test
    public void insertUserTest() {
        when(userService.insertUser(any(User.class))).thenReturn(any(Integer.class));
    }
}
