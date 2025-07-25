package com.backend.spendwise.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.spendwise.dto.AuthDTO;
import com.backend.spendwise.dto.ProfileDTO;
import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.ProfileRepository;

// This service handles profile-related operations such as registration and conversion between DTO and entity.
@Service
public class ProfileService 
{

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ProfileDTO registerProfile(ProfileDTO profileDTO) 
    {
        ProfileEntity newProfile = toEntity(profileDTO);

        newProfile.setIsActive(true); // Set default active status
        newProfile.setActivationToken(UUID.randomUUID().toString()); // Clear activation token if not needed

        newProfile = profileRepository.save(newProfile);

        String activationLink = "http://localhost:8080/profile/activate/" + newProfile.getActivationToken();
        String emailSubject = "Activate your SpendWise account";
        String emailBody = "Welcome to SpendWise! Please activate your account by clicking the link below:\n" + activationLink;

        // Send activation email
        System.out.println("Sending activation email to: " + newProfile.getEmail());
        emailService.sendEmail(newProfile.getEmail(), emailSubject, emailBody);

        return toDTO(newProfile);
    }
    
    public ProfileEntity toEntity(ProfileDTO profileDTO) 
    {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(passwordEncoder.encode(profileDTO.getPassword())) // Encode password
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();
    }

    public ProfileDTO toDTO(ProfileEntity profileEntity) 
    {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }

    public Boolean isAccountActive(String email)
    {
        ProfileEntity profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profile not found with email: " + email));
        return profile.getIsActive(); 
    }

    public ProfileEntity getCurrentProfile() 
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Current authentication: " + authentication);

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String email = authentication.getName(); // Get the email from the authentication object
        return profileRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profile not found for email: " + email));
    }

    public ProfileDTO getPublicProfile(String email) 
    {
        ProfileEntity currentUser = null;
        if(email == null || email.isEmpty()) {
            currentUser = getCurrentProfile();
        } else {
            currentUser = profileRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Profile not found for email: " + email));
        }

        return ProfileDTO.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .profileImageUrl(currentUser.getProfileImageUrl())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();
    }

    public Map<String, Object> authenticationAndGenerateToken(AuthDTO authDTO) 
    {
        try 
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
            return Map.of(
                "token", "JWT TOKEN", // Generate a dummy token for now
                "user", getPublicProfile(authDTO.getEmail())
            );
        } catch (Exception e) 
        {
            return Map.of("message", "Authentication failed: " + e.getMessage());
        }
    }
                                        
}
