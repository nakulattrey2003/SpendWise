package com.backend.spendwise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.spendwise.dto.ProfileDTO;
import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.ProfileRepository;
import com.backend.spendwise.service.ProfileService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping()
public class HomeController 
{
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;    

    @GetMapping({"/status", "/health"})
    public String HealthCheck() 
    {
        return "Application is running successfully!!!";
    }
    
    
    @GetMapping({"/profile", "/myProfile", "/me"})
    public ResponseEntity<Optional<ProfileEntity>> myProfile() 
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

    @PutMapping(value = "/updateProfile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileDTO> updateProfile(
            @PathVariable Long id,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) 
    {
        ProfileDTO profileDTO = ProfileDTO.builder()
                .id(id)
                .fullName(fullName)
                .email(email)
                .password(password)
                .profileImage(profileImage)
                .build();

        ProfileDTO updatedProfile = profileService.updateProfile(profileDTO);

        return ResponseEntity.ok(updatedProfile);
    }

}
