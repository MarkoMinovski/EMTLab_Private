package com.emt.springbackendapi.security;
import com.emt.springbackendapi.model.domain.User;
import com.emt.springbackendapi.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTHelper jwtHelper;
    private final UserService userService;

    public JWTFilter(JWTHelper jwtHelper, UserService userService) {
        this.jwtHelper = jwtHelper;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        String headerValue = request.getHeader(JWTConstants.HEADER);
        if (headerValue == null || !headerValue.startsWith(JWTConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = headerValue.substring(JWTConstants.TOKEN_PREFIX.length());

        try {
            String username = jwtHelper.extractUsername(token);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (username == null || authentication != null) {
                filterChain.doFilter(request, response);
                return;
            }

            Optional<User> findByUsernameResult = userService.findByUsername(username);
            User user = null;
            if (findByUsernameResult.isPresent()) {
                user = findByUsernameResult.get();
            }

            if (jwtHelper.isValid(token, user)) {
                assert user != null;
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (JwtException jwtException) {
            // TODO: Add logic for exception handling.
        }

        filterChain.doFilter(request, response);
    }

}

