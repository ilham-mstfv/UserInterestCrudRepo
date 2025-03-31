package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.UserAcc;
import com.example.userinterestcrudrepo.models.AuthRequest;
import com.example.userinterestcrudrepo.repository.UserAccJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    public AuthService(
            JwtService jwtService,
            UserAccDetailsService userAccDetailsService,
            PasswordEncoder passwordEncoder,
            UserAccJpaRepository userAccJpaRepository) {
        this.jwtService = jwtService;
        this.userAccDetailsService = userAccDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userAccJpaRepository = userAccJpaRepository;
    }

    public String authByRequest(AuthRequest request) {
        return this.tryLoadUserByUsername(request.getUsername())
                .filter(userDetails ->
                        this.validatePassword(request.getPassword(), userDetails.getPassword()))
                .map(jwtService::generateToken)
                .orElseGet(() -> registerAndGenerateTokenByRequest(request));
    }

    private Optional<UserDetails> tryLoadUserByUsername(String username) {
        try {
            return Optional.of(userAccDetailsService.loadUserByUsername(username));
        } catch (UsernameNotFoundException e) {
            return Optional.empty();
        }
    }

    private boolean validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalArgumentException("Wrong password");
        }
        return true;
    }

    private String registerAndGenerateTokenByRequest(AuthRequest request) {
        UserAcc userAcc = new UserAcc(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()));

        UserDetails userDetails = User.builder()
                .username(userAcc.getUsername())
                .password(userAcc.getPassword())
                .roles("USER")
                .build();

        userAccJpaRepository.insertUserAcc(userAcc);
        return jwtService.generateToken(userDetails);
    }
}
