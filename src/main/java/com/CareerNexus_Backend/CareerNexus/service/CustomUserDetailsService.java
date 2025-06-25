package com.CareerNexus_Backend.CareerNexus.service;


import com.CareerNexus_Backend.CareerNexus.model.User;
import com.CareerNexus_Backend.CareerNexus.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User userFromDb = userAuthRepository.findByUserId(username)
                .orElseThrow(() -> {
                    System.out.println("User not found: " + username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });


        return org.springframework.security.core.userdetails.User.builder()
                .username(userFromDb.getUserId())
                .password(userFromDb.getPassword())
                .roles(userFromDb.getRole())
                .build();
    }
}
