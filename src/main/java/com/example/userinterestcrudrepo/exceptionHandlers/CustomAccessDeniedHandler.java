package com.example.userinterestcrudrepo.exceptionHandlers;


import com.example.userinterestcrudrepo.models.ApiResponse;
import com.example.userinterestcrudrepo.services.UserIpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final UserIpService userIpService;

    public CustomAccessDeniedHandler(UserIpService userIpService) {
        this.userIpService = userIpService;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        userIpService.createAndInsertUserIpByRequest(request, "unauthorized");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(ApiResponse.error(
                        403,
                        "Forbidden",
                        accessDeniedException.getMessage()
                ))
        );
    }
}
