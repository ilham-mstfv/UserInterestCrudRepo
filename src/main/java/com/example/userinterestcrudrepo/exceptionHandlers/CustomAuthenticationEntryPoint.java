package com.example.userinterestcrudrepo.exceptionHandlers;

import com.example.userinterestcrudrepo.models.ApiResponse;
import com.example.userinterestcrudrepo.services.UserIpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final UserIpService userIpService;

    public CustomAuthenticationEntryPoint(UserIpService userIpService) {
        this.userIpService = userIpService;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        userIpService.createAndInsertUserIpByRequest(request, "unauthorized");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(ApiResponse.error(
                        401,
                        "Unauthorized",
                        authException.getMessage()
                                + ". Use the correct token for authentication"
                ))
        );
    }
}

