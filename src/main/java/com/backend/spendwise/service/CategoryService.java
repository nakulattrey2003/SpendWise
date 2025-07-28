package com.backend.spendwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.spendwise.dto.CategoryDTO;
import com.backend.spendwise.entity.CategoryEntity;
import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.CategoryRepository;

@Service
public class CategoryService 
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProfileService profileService;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) 
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        Boolean categoryExists = categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId());

        if (categoryExists) throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with this name already exists for this profile");
        else 
        {
            CategoryEntity newCategory = toEntity(categoryDTO, profile);
            newCategory = categoryRepository.save(newCategory);

            return toDTO(newCategory);
        }
    }

    public CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profile) 
    {
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .type(categoryDTO.getType())
                .icon(categoryDTO.getIcon())
                .profile(profile)
                .build();
    }

    public CategoryDTO toDTO(CategoryEntity categoryEntity) 
    {
        return CategoryDTO.builder()
                .id(categoryEntity.getId())
                .profileId(categoryEntity.getProfile() != null ? categoryEntity.getProfile().getId() : null)
                .name(categoryEntity.getName())
                .createdAt(categoryEntity.getCreatedAt())
                .updatedAt(categoryEntity.getUpdatedAt())
                .type(categoryEntity.getType())
                .icon(categoryEntity.getIcon())
                .build();
    }

        
}
