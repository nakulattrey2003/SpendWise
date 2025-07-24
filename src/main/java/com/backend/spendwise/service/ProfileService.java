package com.backend.spendwise.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.spendwise.dto.ProfileDTO;
import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.ProfileRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailService emailService;

    public ProfileDTO registerProfile(ProfileDTO profileDTO) {
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
    
    public ProfileEntity toEntity(ProfileDTO profileDTO) {
        return ProfileEntity.builder()
                .id(profileDTO.getId())
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();
    }

    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }

    @PostConstruct
    public void init() {
        System.out.println("🔥🔥🔥 INJECTED: " + profileRepository + " 🔥🔥🔥");
    }
}
