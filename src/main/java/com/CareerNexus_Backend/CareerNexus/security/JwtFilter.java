package com.CareerNexus_Backend.CareerNexus.security;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component // Important: Mark this class as a Spring component so it can be autowired
public abstract class JwtFilter extends OncePerRequestFilter {

}
