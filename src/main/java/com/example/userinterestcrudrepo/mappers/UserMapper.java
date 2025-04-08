package com.example.userinterestcrudrepo.mappers;


import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.models.UserRequest;
import com.example.userinterestcrudrepo.repository.InterestJpaRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "interests", target = "interests", qualifiedByName = "fetchInterestsByName")
    User userRequestToUser(UserRequest userRequest, @Context InterestJpaRepository repo);

    @Named("fetchInterestsByName")
    default List<Interest> fetchInterestsByName(List<String> interests, @Context InterestJpaRepository repo) {
        return repo.findInterestsByNameIn(interests);
    }
}