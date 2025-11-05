package org.example.storemanagementbestpractice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom failure handler that prevents redirects and returns a 401 Unauthorized
 * status with a JSON body for failed REST API authentication.
 */
public class RestAuthFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String errorMessage = "Authentication Failed: Invalid credentials.";

        String clientId = request.getParameter("client_id");

        if (exception instanceof BadCredentialsException) {
            errorMessage = "Incorrect username or password.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "User account is disabled.";
        }

        // Constructing the Error Response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", errorMessage);
        errorResponse.put("path", request.getRequestURI());

        if (clientId != null) {
            errorResponse.put("clientContext", "Client ID: " + clientId);
        }

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}