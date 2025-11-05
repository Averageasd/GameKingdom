package org.example.storemanagementbestpractice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import java.util.Map;

/**
 * Custom success handler that prevents redirects and returns a 200 OK status
 * with a JSON body for successful REST API authentication.
 */
public class RestAutheSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // Clear potential session cookie before sending success response
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        // Write a more detailed success message to the response body
        Map<String, String> responseBody = Map.of(
                "message", "Authentication successful",
                "username", authentication.getName(), // Get the username from the Authentication object
                "tokenType", "Session" // Since you are using session-based login
        );

        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}