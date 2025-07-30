package com.backend.spendwise.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.spendwise.dto.CategoryDTO;
import com.backend.spendwise.entity.CategoryEntity;
import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService 
{
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) 
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        Boolean categoryExists = categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId());

        if (categoryExists) throw new RuntimeException("Category with this name already exists for this profile");
        else 
        {
            CategoryEntity newCategory = toEntity(categoryDTO, profile);
            newCategory = categoryRepository.save(newCategory);

            return toDTO(newCategory);
        }
    }

    public List<CategoryDTO> getCategoriesForCurrentUser() 
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> categoryEntities = categoryRepository.findAllByProfileId(profile.getId());
        
        if (categoryEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No categories found for this profile");
        }
        
        return categoryEntities.stream()  // Traversing the List and Converting each CategoryEntity to CategoryDTO 
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<CategoryDTO> getCategoriesByType(String type) 
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> categoryEntities = categoryRepository.findByTypeAndProfileId(type, profile.getId());

        if (categoryEntities.isEmpty()) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No categories found for this type");
        }

        return categoryEntities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) 
    {
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(id, profile.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        if(categoryDTO.getName() != null) existingCategory.setName(categoryDTO.getName());
        if(categoryDTO.getType() != null) existingCategory.setType(categoryDTO.getType());
        if(categoryDTO.getIcon() != null) existingCategory.setIcon(categoryDTO.getIcon());

        CategoryEntity updatedCategory = categoryRepository.save(existingCategory);
        return toDTO(updatedCategory);
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
