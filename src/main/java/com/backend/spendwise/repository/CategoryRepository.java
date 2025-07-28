package com.backend.spendwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.spendwise.entity.CategoryEntity;
import com.backend.spendwise.entity.ProfileEntity;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    // sql query = // SELECT * FROM categories WHERE profile_id = ?1
    List<CategoryEntity> findByProfileId(Long profileId);

    // sql query = // SELECT * FROM categories WHERE id = ?1 AND profile_id = ?2
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

    // sql query = // SELECT * FROM categories WHERE type = ?1 AND profile_id = ?2
    Optional<CategoryEntity> findByTypeAndProfile(String type, ProfileEntity profile);
    
    // sql query = // SELECT * FROM categories WHERE name = ?1 AND profile_id = ?2
    Boolean existsByNameAndProfileId(String name, Long profileId);
}
