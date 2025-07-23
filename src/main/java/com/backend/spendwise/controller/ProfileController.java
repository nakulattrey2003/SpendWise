package com.backend.spendwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.spendwise.dto.ProfileDTO;
import com.backend.spendwise.service.ProfileService;

@RestController
public class ProfileController 
{
    @Autowired
    private ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO) 
    {
        ProfileDTO createdProfile = profileService.registerProfile(profileDTO);
        return ResponseEntity.ok().body(createdProfile);
    }
    
}
