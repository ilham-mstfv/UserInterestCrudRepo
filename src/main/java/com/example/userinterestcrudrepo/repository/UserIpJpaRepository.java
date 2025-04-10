package com.example.userinterestcrudrepo.repository;

import com.example.userinterestcrudrepo.entities.UserIp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserIpJpaRepository extends JpaRepository<UserIp, Integer> {

    UserIp save(UserIp userIp);

    Optional<UserIp> findById(int id);

    List<UserIp> findAll();
}
