package com.example.userinterestcrudrepo.mappers;


import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.models.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User userRequestToUser(UserRequest userRequest);
}