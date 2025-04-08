package com.example.userinterestcrudrepo.mappers;

import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.models.InterestRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InterestMapper {

    @Mapping(target = "id", ignore = true)
    Interest interestRequestToInterest(InterestRequest interestRequest);
}
