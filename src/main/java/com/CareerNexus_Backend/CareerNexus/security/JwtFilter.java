package com.CareerNexus_Backend.CareerNexus.security;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public abstract class JwtFilter extends OncePerRequestFilter {

}
