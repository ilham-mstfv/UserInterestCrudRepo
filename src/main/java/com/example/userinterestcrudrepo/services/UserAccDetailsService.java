package com.example.userinterestcrudrepo.services;

import com.example.userinterestcrudrepo.entities.UserAcc;
import com.example.userinterestcrudrepo.repository.UserAccJpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccDetailsService implements UserDetailsService {

    private final UserAccJpaRepository userAccJpaRepository;

    public UserAccDetailsService(UserAccJpaRepository userAccJpaRepository) {
        this.userAccJpaRepository = userAccJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var acc = Optional.ofNullable(userAccJpaRepository.getUserAccByUsername(username))
                .orElseThrow(() ->
                        new UsernameNotFoundException("No User Account found with username: " + username));

        return this.convertToUserDetails(acc);
    }

    private UserDetails convertToUserDetails(UserAcc userAcc) {
        return User.builder()
                .username(userAcc.getUsername())
                .password(userAcc.getPassword())
                .roles("USER")
                .build();
    }
}
