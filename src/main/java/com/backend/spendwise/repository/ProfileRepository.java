package com.backend.spendwise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.spendwise.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

     // sql query = "SELECT * FROM profiles WHERE email = ?1"
     Optional<ProfileEntity> findByEmail(String email);
    
}
