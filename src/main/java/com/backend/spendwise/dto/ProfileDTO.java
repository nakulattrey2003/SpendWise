    package com.backend.spendwise.dto;

    import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

// import jakarta.persistence.Lob;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;

    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class ProfileDTO 
    {
        private Long id;
        private String fullName;
        private String email;
        private String password;
        private String profileImageUrl;
        private MultipartFile profileImage;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
