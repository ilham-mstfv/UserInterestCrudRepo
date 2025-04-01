package com.example.userinterestcrudrepo.config;

import com.example.userinterestcrudrepo.exceptionHandlers.CustomAccessDeniedHandler;
import com.example.userinterestcrudrepo.exceptionHandlers.CustomAuthenticationEntryPoint;
import com.example.userinterestcrudrepo.services.JwtFilterService;
import com.example.userinterestcrudrepo.services.UserAccDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private UserAccDetailsService userDetailsService;
    private JwtFilterService jwtFilterService;

    public SecurityConfig(
            UserAccDetailsService userDetailsService,
            JwtFilterService jwtFilterService
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtFilterService = jwtFilterService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/users/auth/log").permitAll()
                    .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilterService, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
