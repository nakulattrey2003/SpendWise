package com.backend.spendwise.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

// This service implements UserDetailsService to load user-specific data for authentication and authorization.
@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService
{
    private final ProfileRepository profileRepository;

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
