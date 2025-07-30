package com.backend.spendwise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping()
public class HomeController 
{
    private final ProfileRepository profileRepository;

    @GetMapping({"/status", "/health"})
    public String HealthCheck() 
    {
        return "Application is running successfully!!!";
    }
    
    
    @GetMapping({"/profile", "/myProfile", "/me"})
    public ResponseEntity<Optional<ProfileEntity>> myProfile() 
    // public String myProfile() 
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) 
        {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
        
        String email = authentication.getName();
        Optional<ProfileEntity> profile = profileRepository.findByEmail(email);
        
        if (profile == null) 
        {
            return ResponseEntity.status(404).body(null); // Not Found
        }
        
        return ResponseEntity.ok(profile);
    }
}
