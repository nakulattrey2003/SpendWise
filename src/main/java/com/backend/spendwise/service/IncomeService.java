package com.backend.spendwise.service;

import org.springframework.stereotype.Service;

import com.backend.spendwise.dto.IncomeDTO;
import com.backend.spendwise.entity.CategoryEntity;
import com.backend.spendwise.entity.IncomeEntity;
import com.backend.spendwise.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService 
{
    
    // helper method to convert IncomeDTO to IncomeEntity
    private IncomeEntity toEntity(IncomeDTO IncomeDTO, ProfileEntity profileEntity, CategoryEntity categoryEntity) 
    {
        return IncomeEntity.builder()
                .name(IncomeDTO.getName())
                .icon(IncomeDTO.getIcon())
                .date(IncomeDTO.getDate())
                .amount(IncomeDTO.getAmount())
                .createdAt(IncomeDTO.getCreatedAt())
                .updatedAt(IncomeDTO.getUpdatedAt())
                .profile(profileEntity)
                .category(categoryEntity)
                .build();
    }

    private IncomeDTO toDTO(IncomeEntity IncomeEntity) 
    {
        return IncomeDTO.builder()
                .id(IncomeEntity.getId())
                .name(IncomeEntity.getName())
                .icon(IncomeEntity.getIcon())
                .date(IncomeEntity.getDate())
                .amount(IncomeEntity.getAmount())
                .categoryId(IncomeEntity != null ? IncomeEntity.getCategory().getId() : null)
                .categoryName(IncomeEntity != null ? IncomeEntity.getCategory().getName() : "N/A")
                .createdAt(IncomeEntity.getCreatedAt())
                .updatedAt(IncomeEntity.getUpdatedAt())
                .build();
    }
}
