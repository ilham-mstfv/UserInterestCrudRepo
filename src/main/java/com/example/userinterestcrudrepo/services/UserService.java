package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.exceptions.InsertUserException;
import com.example.userinterestcrudrepo.exceptions.NoUsersFoundException;
import com.example.userinterestcrudrepo.mappers.UserMapper;
import com.example.userinterestcrudrepo.models.UserFilterRequest;
import com.example.userinterestcrudrepo.models.UserRequest;
import com.example.userinterestcrudrepo.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserJpaRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(
            UserJpaRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User createUserByRequest(UserRequest userRequest) {
        return userMapper.userRequestToUser(userRequest);
    }

    public int insertUser(User user) {
        try {
            userRepository.insertUser(user);
            return user.getId();
        } catch (Exception err) { // TODO: Добавить обработку других ошибок
            throw new InsertUserException("Error inserting user " + user, err);
        }
    }

    public int createAndInsertUserByRequest(UserRequest userRequest) {
        return this.insertUser(
                createUserByRequest(userRequest));
    }

    public List<User> getAllUsers() {
        List<User> userList = userRepository.getAllUsers();
        if (userList.isEmpty()) {
            throw new NoUsersFoundException("No users found");
        }
        return userList;
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new NoUsersFoundException("No user found with id " + id));
    }

    public List<User> findUsersByFilter(UserFilterRequest filter) {
        List<User> userList = userRepository.getUsersByFilter(filter);
        if (userList.isEmpty()) {
            throw new NoUsersFoundException("No users found");
        }
        return userList;
    }
}
