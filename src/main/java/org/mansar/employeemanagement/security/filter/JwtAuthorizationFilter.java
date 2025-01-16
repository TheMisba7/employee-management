package org.mansar.employeemanagement.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mansar.employeemanagement.config.SecurityConfig;
import org.mansar.employeemanagement.security.service.JwtService;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.context.annotation.Lazy;
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

    public JwtAuthorizationFilter(JwtService jwtService, @Lazy IUserService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldProcess(request.getRequestURI())) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null) {
                 String jwt = authHeader.substring(7);
                String username = jwtService.extractUsername(jwt);
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
