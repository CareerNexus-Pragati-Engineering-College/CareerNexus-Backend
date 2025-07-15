package com.CareerNexus_Backend.CareerNexus.security;

import com.CareerNexus_Backend.CareerNexus.service.CustomUserDetailsService;
// Correct IOException import for java.io.IOException
import java.io.IOException; // Corrected import
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired; // <--- ADD THIS IMPORT
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtFilter extends OncePerRequestFilter {

    // --- FIX IS HERE: Use constructor injection (recommended) or @Autowired ---
    private final JwtUtils jwtUtils; // Make them final if using constructor injection
    private final CustomUserDetailsService customUserDetailsService;

    // Constructor Injection: Spring will automatically provide these beans
    @Autowired // This annotation is optional for constructor injection if there's only one constructor in Spring 4.3+
    public JwtFilter(JwtUtils jwtUtils, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customUserDetailsService = customUserDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { // Corrected IOException import above

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                // Ensure jwtUtils.extractUsername handles null/empty jwt gracefully, though it should be non-null here
                username = jwtUtils.extractUsername(jwt);
            } catch (Exception e) {
                System.err.println("Error extracting username from JWT: " + e.getMessage());

            }
        }

        // Only proceed if a username was extracted and no authentication is currently in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;
            try {
                // This will throw UsernameNotFoundException if user doesn't exist
                userDetails = this.customUserDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                System.err.println("User not found from token: " + username + " - " + e.getMessage());

            }


            if (userDetails != null && jwtUtils.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authenticated user: " + username); // For debugging
            } else {
                System.out.println("JWT is invalid for user: " + username + (userDetails == null ? " (UserDetails null)" : "")); // For debugging
            }
        }

        // Continue the filter chain. This is crucial!
        filterChain.doFilter(request, response);
    }
}