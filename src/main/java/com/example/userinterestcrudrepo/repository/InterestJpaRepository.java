package com.example.userinterestcrudrepo.repository;

import com.example.userinterestcrudrepo.entities.Interest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface InterestJpaRepository extends JpaRepository<Interest, Integer> {

    Interest save(Interest interest);

    void delete(Interest interest);

    List<Interest> findInterestsByNameIn(List<String> name);
}
