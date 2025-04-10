package com.example.userinterestcrudrepo;

import com.example.userinterestcrudrepo.entities.UserAcc;
import com.example.userinterestcrudrepo.models.UserRole;
import com.example.userinterestcrudrepo.repository.UserAccJpaRepository;
import com.example.userinterestcrudrepo.services.JwtService;
import com.example.userinterestcrudrepo.services.UserAccDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = "com.example.userinterestcrudrepo")
public class UserInterestCrudRepoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserInterestCrudRepoApplication.class, args);
    }

}

@Slf4j
@Component
class UserAccCommandLineRunner implements CommandLineRunner {

    private final UserAccJpaRepository userAccJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserAccDetailsService userAccDetailsService;

    UserAccCommandLineRunner(
            UserAccJpaRepository userAccJpaRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            UserAccDetailsService userAccDetailsService
    ) {
        this.userAccJpaRepository = userAccJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userAccDetailsService = userAccDetailsService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        UserAcc userAcc = new UserAcc(
                "tempAdmin",
                passwordEncoder.encode("tempAdmin"),
                UserRole.ADMIN);
        
        String token = jwtService.generateToken(
                userAccDetailsService.convertToUserDetails(userAcc), 60000);

        userAccJpaRepository.insertUserAcc(userAcc);

        log.warn("UserAcc '{}' was saved. Token: <{}>",
                userAcc.getUsername(),
                token);
    }
}