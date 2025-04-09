package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.Interest;
import com.example.userinterestcrudrepo.mappers.InterestMapper;
import com.example.userinterestcrudrepo.models.InterestRequest;
import com.example.userinterestcrudrepo.repository.InterestJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {

    private final InterestJpaRepository interestJpaRepository;
    private final InterestMapper interestMapper;

    @Autowired
    public InterestService(
            InterestJpaRepository interestJpaRepository,
            InterestMapper interestMapper
    ) {
        this.interestJpaRepository = interestJpaRepository;
        this.interestMapper = interestMapper;
    }

    public Interest createInterestByRequest(InterestRequest interestRequest) {
        return interestMapper.interestRequestToInterest(interestRequest);
    }
    
    public int insertInterest(Interest interest) {
        try {
            interestJpaRepository.save(interest);
            return interest.getId();
        } catch (Exception err) { // TODO: Добавить обработку других ошибок
            throw new RuntimeException("Error inserting interest" + interest.getName(), err);
        }
    }

    public int createAndInsertInterestByRequest(InterestRequest interestRequest) {
        return this.insertInterest(
                createInterestByRequest(interestRequest));
    }

    public List<Interest> getAllInterests() {
        return interestJpaRepository.findAll();
    }
}
