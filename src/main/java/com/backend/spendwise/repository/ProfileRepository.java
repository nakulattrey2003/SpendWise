package com.backend.spendwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.spendwise.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository {
    <Optional> ProfileEntity findByEmail(String email);
    
}
