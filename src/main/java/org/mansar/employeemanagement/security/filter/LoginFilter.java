package org.mansar.employeemanagement.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mansar.employeemanagement.dto.LoginResponse;
import org.mansar.employeemanagement.security.service.JwtService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public LoginFilter(JwtService jwtService, ObjectMapper objectMapper, @Lazy AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        super.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            JsonNode jsonNode = objectMapper.readTree(request.getReader());
            String username = jsonNode.get("username").asText("");
            String password = jsonNode.get("password").asText();
            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails principal = (UserDetails) authResult.getPrincipal();
        LoginResponse loginResponse =
                new LoginResponse(jwtService.generateToken(principal), jwtService.getExpirationTime());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}
