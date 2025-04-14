package com.example.userinterestcrudrepo.controller;

import com.example.userinterestcrudrepo.controllers.UserController;
import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.exceptions.NoUsersFoundException;
import com.example.userinterestcrudrepo.models.*;
import com.example.userinterestcrudrepo.models.filters.AgeFilter;
import com.example.userinterestcrudrepo.models.filters.BalanceFilter;
import com.example.userinterestcrudrepo.models.requests.UserFilterRequest;
import com.example.userinterestcrudrepo.models.requests.UserRequest;
import com.example.userinterestcrudrepo.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    static User user1 =  new User(
            1, "John Doe", 20,
            List.of(new Interest(1, "soccer", "sport", "some sport")),
            Country.AZE, Lang.AZE, BigDecimal.valueOf(100.00)
    );

    static User user2 = new User(
            2, "Jim Kerry", 34,
            List.of(new Interest(2, "Math", "science", "some science"),
                    new Interest(3, "philosophy", "science", "some science")),
            Country.AZE, Lang.RUS, BigDecimal.valueOf(1220.20)
    );


    static UserRequest user1Request = new UserRequest(
            "John Doe", 20,
            List.of(new Interest(1, "soccer", "sport", "some sport")),
            Country.AZE, Lang.AZE, BigDecimal.valueOf(100.00)
    );

    static UserRequest user2Request = new UserRequest(
            "Jim Kerry", 34,
            List.of(new Interest(2, "Math", "science", "some science"),
                    new Interest(3, "philosophy", "science", "some science")),
            Country.AZE, Lang.RUS, BigDecimal.valueOf(1220.20)
    );


    static UserFilterRequest user1FilterRequest = new UserFilterRequest(
            "John Doe",
            new AgeFilter(10, 40),
            Country.AZE, Lang.RUS,
            new BalanceFilter(BigDecimal.valueOf(10.0), BigDecimal.valueOf(200.4))
    );


    static UserRequest nonValidUserRequest = new UserRequest(
            "", 300, List.of(), Country.AZE, Lang.AZE, BigDecimal.valueOf(-1.0)
    );


    @Test
    public void findById_ReturnUser_WhenFound() throws Exception {
        when(userService.getUserById(user1.getId()))
                .thenReturn(user1);

        mockMvc.perform(get("/users/" + user1.getId()))
                .andExpectAll(buildApiResponseMatchers(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.data.id").value(user1.getId()));
    }

    @Test
    public void findById_ReturnUser_WhenNotFound() throws Exception {
        when(userService.getUserById(0))
                .thenThrow(new NoUsersFoundException("No user found with id 0"));

        mockMvc.perform(get("/users/0"))
                .andExpectAll(buildApiResponseMatchers(StatusCode.SERVER_ERROR))
                .andExpect(jsonPath("$.data").value("No user found with id 0"))
        ;
    }

    @Test
    public void getAllUsers_ReturnUsers_WhenFound() throws Exception {
        List<User> users = List.of(user1, user2);

        when(userService.getAllUsers())
                .thenReturn(users);

        mockMvc.perform(get("/users/all"))
                .andExpectAll(buildApiResponseMatchers(StatusCode.SUCCESS))
                .andExpectAll(buildUserResultMatchers(users))
        ;
    }

    @Test
    public void getAllUsers_ReturnUsers_WhenNotFound() throws Exception {
        when(userService.getAllUsers())
                .thenThrow(new NoUsersFoundException("No users found"));

        mockMvc.perform(get("/users/all"))
                .andExpectAll(buildApiResponseMatchers(StatusCode.SERVER_ERROR))
                .andExpect(jsonPath("$.data").value("No users found"))
        ;
    }

    @Test
    public void addUser_ReturnUserId_WhenAdded() throws Exception {
        when(userService.createAndInsertUserByRequest(any(UserRequest.class)))
                .thenReturn(10);

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user1Request)))
                .andExpectAll(buildApiResponseMatchers(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.data.user.id").value(10))
        ;
    }

    @Test
    public void addUser_ReturnUserId_WhenJsonValidationException() throws Exception {
        when(userService.createAndInsertUserByRequest(any(UserRequest.class)))
                .thenReturn(10);

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf("JsonValidationException")))
                .andExpectAll(buildApiResponseMatchers(StatusCode.JSON_VALIDATION_ERROR))
        ;
    }

    @Test
    public void addUser_ReturnUserId_WhenUserRequestFieldValueValidationException() throws Exception {
        when(userService.createAndInsertUserByRequest(any(UserRequest.class)))
                .thenReturn(10);

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(nonValidUserRequest)))
                .andExpectAll(buildApiResponseMatchers(StatusCode.VALIDATION_ERROR))
        ;
    }

    @Test
    public void findUsersByFilter_ReturnUsers_WhenFound() throws Exception {
        List<User> users = List.of(user1, user2);

        when(userService.findUsersByFilter(any(UserFilterRequest.class)))
                .thenReturn(users);

        mockMvc.perform(post("/users/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user1FilterRequest)))
                .andExpectAll(buildApiResponseMatchers(StatusCode.SUCCESS))
                .andExpectAll(buildUserResultMatchers(users))
        ;
    }

    @Test
    public void findUsersByFilter_ReturnUsers_WhenNotFound() throws Exception {
        when(userService.findUsersByFilter(any(UserFilterRequest.class)))
                .thenThrow(new NoUsersFoundException("No users found"));

        mockMvc.perform(post("/users/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(user1FilterRequest)))
                .andExpectAll(buildApiResponseMatchers(StatusCode.SERVER_ERROR))
        ;
    }


    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static ResultMatcher[] buildUserResultMatchers(List<User> users) {
        List<ResultMatcher> resultMatchers = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            resultMatchers.addAll(List.of(
                    jsonPath("$.data[" + i + "].id").value(users.get(i).getId()),
                    jsonPath("$.data[" + i + "].personalData").value(users.get(i).getPersonalData()),
                    jsonPath("$.data[" + i + "].age").value(users.get(i).getAge()),
                    jsonPath("$.data[" + i + "].interests").value(users.get(i).getInterests().stream()
                            .map(interest -> Map.of(
                                    "id", interest.getId(),
                                    "name", interest.getName(),
                                    "tag", interest.getTag(),
                                    "description", interest.getDescription()
                            )).collect(Collectors.toList())),
                    jsonPath("$.data[" + i + "].country").value(users.get(i).getCountry().name()),
                    jsonPath("$.data[" + i + "].lang").value(users.get(i).getLang().name()),
                    jsonPath("$.data[" + i + "].balance").value(users.get(i).getBalance())
            ));
        }

        return resultMatchers.toArray(new ResultMatcher[0]);
    }

    public static ResultMatcher[] buildApiResponseMatchers(StatusCode statusCode) {
        return new ResultMatcher[]{
                content().contentType(MediaType.APPLICATION_JSON),
                status().is(statusCode.getHttpStatus()),
                jsonPath("$.code").value(statusCode.getCode()),
                jsonPath("$.message").value(statusCode.getMessage())
        };
    }
}
