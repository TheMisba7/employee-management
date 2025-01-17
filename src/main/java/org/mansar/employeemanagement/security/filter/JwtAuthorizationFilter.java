package org.mansar.employeemanagement.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mansar.employeemanagement.config.SecurityConfig;
import org.mansar.employeemanagement.dto.ApiError;
import org.mansar.employeemanagement.security.service.JwtService;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final IUserService userDetailsService;
    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(JwtService jwtService, @Lazy IUserService userDetailsService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldProcess(request.getRequestURI())) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null) {
                 String jwt = authHeader.substring(7);
                String username = "";
                try {
                    username = jwtService.extractUsername(jwt);
                } catch (ExpiredJwtException ex) {
                    logger.error(ex.getMessage(), ex);
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.getWriter()
                            .write(objectMapper.writeValueAsString(new ApiError(HttpStatus.BAD_REQUEST.value(), "invalid token")));
                    response.setContentType("application/json");
                    response.flushBuffer();
                }
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean shouldProcess(String uri) {
        for (String rq: SecurityConfig.ALLOWED_REQUEST) {
            if (rq.equals(uri))
                return false;
        }
        return true;
    }
}
