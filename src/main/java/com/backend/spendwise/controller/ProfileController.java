package com.backend.spendwise.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.spendwise.dto.AuthDTO;
import com.backend.spendwise.dto.ProfileDTO;
import com.backend.spendwise.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProfileController 
{
    private ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileDTO> registerProfile(@RequestBody ProfileDTO profileDTO) 
    {
        ProfileDTO createdProfile = profileService.registerProfile(profileDTO);
        return ResponseEntity.ok().body(createdProfile);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginProfile(@RequestBody AuthDTO authDTO) 
    {
        try 
        {
            if(!profileService.isAccountActive(authDTO.getEmail())) 
            {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Profile is not active. Please activate your account."));
            }
            else
            {
                Map<String, Object> response = profileService.authenticationAndGenerateToken(authDTO);
                return ResponseEntity.ok(response);
            }
        } 
        catch (Exception e) 
        {
            return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", e.getMessage()));
        }
    }

    
    
}
