package com.backend.spendwise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.spendwise.dto.CategoryDTO;
import com.backend.spendwise.service.CategoryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController 
{
    private final CategoryService categoryService;
    
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) 
    {
        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping("/getCategories")
    public ResponseEntity<List<CategoryDTO>> getCategories() 
    {
        List<CategoryDTO> categoryDTOs = categoryService.getCategoriesForCurrentUser();
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/getCategoriesByType")    
    public ResponseEntity<List<CategoryDTO>> getCategoriesByType(@RequestParam String type) 
    {
        List<CategoryDTO> categoryDTOs = categoryService.getCategoriesByType(type);
        if (categoryDTOs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(categoryDTOs);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) 
    {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }
    
    
}
