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
        String username = request.getUsername();
        String password = request.getPassword();
        UserDetails userDetails;

        try {
            userDetails = userAccDetailsService.loadUserByUsername(username);

            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new IllegalArgumentException("Wrong password");
            }
        } catch (UsernameNotFoundException e) {
            var hashedPassword = passwordEncoder.encode(password);
            userDetails = User.builder()
                    .username(username)
                    .password(hashedPassword)
                    .roles("USER")
                    .build();
            userAccJpaRepository.insertUserAcc(new UserAcc(username, hashedPassword));
        }

        return jwtService.generateToken(userDetails);
    }
}
