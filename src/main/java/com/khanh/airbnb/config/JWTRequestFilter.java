package com.khanh.airbnb.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.repositories.UserRepository;
import com.khanh.airbnb.services.JWTService;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private JWTService jwtService;
    private UserRepository userRepository;

    public JWTRequestFilter(JWTService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/register") || path.equals("/login") || path.equals("error");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWTRequestFilter running on: " + request.getRequestURI());
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header", request.getRequestURI());
            return;
        }

        String token = tokenHeader.substring(7);
        try {
            jwtService.validateToken(token);
            String username = jwtService.getUsername(token);
            Optional<UserEntity> opUser = userRepository.findByUsername(username);
            if (opUser.isEmpty()) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Username is not incorrect", request.getRequestURI());
                return;
            }

            UserEntity user = opUser.get();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (TokenExpiredException ex) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has expired", request.getRequestURI());
        } catch (JWTDecodeException ex) {
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token", request.getRequestURI());
        } catch (Exception ex) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error " + ex.getMessage(), request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message, String path) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        String json = String.format("""
                {
                  "timestamp": "%s",
                  "status": %d,
                  "error": "%s",
                  "message": "%s",
                  "path": "%s"
                }
                """, LocalDateTime.now(), status,
                status == 401 ? "Unauthorized" : "Error",
                message, path);

        response.getWriter().write(json);
    }
}
