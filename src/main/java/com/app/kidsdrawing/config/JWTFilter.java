package com.app.kidsdrawing.config;

import org.springframework.web.filter.OncePerRequestFilter;

import com.app.kidsdrawing.util.AuthUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {
    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals("/api/v1/auth") &&
                !request.getServletPath().equals("/api/v1/auth/refresh")) {
            String authHeader = request.getHeader("Authorization");
            Optional<String> jwtOpt = authUtil.getJwtFromBearer(authHeader);
            if (jwtOpt.isPresent()) {
                String jwt = jwtOpt.get();
                try {
                    UsernamePasswordAuthenticationToken authenticationToken = authUtil.getAuthenticationFromToken(jwt);
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException e) {
                    log.warn(e.getMessage());
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}