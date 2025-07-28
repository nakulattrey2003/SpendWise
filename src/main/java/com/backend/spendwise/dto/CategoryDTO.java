package com.backend.spendwise.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO 
{
    private Long id;
    private Long profileId; // Assuming this is the ID of the profile associated with the category
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String type; // e.g., "income", "expense"
    private String icon;
}
