package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.UserAcc;
import com.example.userinterestcrudrepo.models.AuthLogRequest;
import com.example.userinterestcrudrepo.models.AuthRegRequest;
import com.example.userinterestcrudrepo.repository.UserAccJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserAccDetailsService userAccDetailsService;
    private final UserAccJpaRepository userAccJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserIpService userIpService;

    @Autowired
    public AuthService(
            JwtService jwtService,
            UserAccDetailsService userAccDetailsService,
            PasswordEncoder passwordEncoder,
            UserAccJpaRepository userAccJpaRepository,
            UserIpService userIpService
    ) {
        this.jwtService = jwtService;
        this.userAccDetailsService = userAccDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userAccJpaRepository = userAccJpaRepository;
        this.userIpService = userIpService;
    }

    public String loginByRequest(AuthLogRequest request) {
        return Optional.ofNullable(
                userAccDetailsService.loadUserByUsername(request.getUsername()))
                .filter(userDetails ->
                        this.validatePassword(request.getPassword(), userDetails.getPassword()))
                .map(jwtService::generateToken)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found or invalid password"));
    }

    public String loginAndSaveUserDataByRequest(AuthLogRequest request, HttpServletRequest httpServletRequest) {
        userIpService.createAndInsertUserIpByRequest(httpServletRequest, request.getUsername());
        return this.loginByRequest(request);
    }

    public String registerByRequest(AuthRegRequest request) {
        userAccJpaRepository.getUserAccByUsername(request.getUsername())
                .ifPresentOrElse(userAcc -> {
                    throw new IllegalArgumentException(
                            "User '"+ request.getUsername() +"' already exists");
                }, () -> userAccJpaRepository.insertUserAcc(new UserAcc(
                        request.getUsername(),
                        passwordEncoder.encode(request.getPassword()),
                        request.getRole()))
                );

        return "User '" + request.getUsername() + "' was registered successfully";
    }

    private boolean validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalArgumentException("Wrong password");
        }
        return true;
    }
}
