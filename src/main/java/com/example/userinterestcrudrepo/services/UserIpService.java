package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.UserIp;
import com.example.userinterestcrudrepo.repository.UserIpJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserIpService {

    private UserIpJpaRepository userIpJpaRepository;

    public UserIpService(UserIpJpaRepository userIpJpaRepository) {
        this.userIpJpaRepository = userIpJpaRepository;
    }


    public UserIp createAndInsertUserIpByRequest(HttpServletRequest request, String username) {
        return userIpJpaRepository.save(new UserIp(
                username,
                request.getRemoteAddr(),
                ZonedDateTime.now(),
                request.getRequestURL().toString(),
                request.getMethod()
        ));
    }

    public List<UserIp> getAll() {
        return userIpJpaRepository.findAll();
    }
}
