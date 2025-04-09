package com.example.userinterestcrudrepo.mappers;


import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.entities.User;
import com.example.userinterestcrudrepo.models.UserRequest;
import com.example.userinterestcrudrepo.repository.InterestJpaRepository;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "interests", target = "interests", qualifiedByName = "fetchInterestsByName")
    User userRequestToUser(UserRequest userRequest, @Context InterestJpaRepository repo);

    @Named("fetchInterestsByName")
    default List<Interest> fetchInterestsByName(List<Object> interests, @Context InterestJpaRepository repo) {
        if (interests.getFirst().getClass() == Integer.class) {
            return repo.findAllById(interests.stream()
                    .map(interest -> (Integer) interest)
                    .collect(Collectors.toList()));
        } else {
            return repo.findInterestsByNameIn(interests.stream()
                    .map(interest -> (String) interest)
                    .collect(Collectors.toList()));
        }
    }
}