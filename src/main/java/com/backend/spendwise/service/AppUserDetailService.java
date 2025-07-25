package com.backend.spendwise.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.ProfileRepository;

// This service implements UserDetailsService to load user-specific data for authentication and authorization.
@Service
public class AppUserDetailService implements UserDetailsService
{
    @Autowired
    private ProfileRepository profileRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        ProfileEntity exsistingProfile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        // Convert ProfileEntity to UserDetails
        return User.builder()
                .username(exsistingProfile.getEmail())
                .password(exsistingProfile.getPassword())
                .authorities(Collections.emptyList()) // Set authorities if needed
                .build();
    }
}
