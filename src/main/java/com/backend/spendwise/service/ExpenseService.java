package com.backend.spendwise.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.spendwise.dto.ExpenseDTO;
import com.backend.spendwise.entity.CategoryEntity;
import com.backend.spendwise.entity.ExpenseEntity;
import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.CategoryRepository;
import com.backend.spendwise.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService 
{
    private final ExpenseRepository expenseRepository;
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;

    public List<ExpenseDTO> readExpensesForCurrentMonth()
    {
        ProfileEntity profileEntity = profileService.getCurrentProfile();

        // Get start and end of current month
        // LocalDate now = LocalDate.now(); // like 2023-10-05
        // LocalDate startDate = now.withDayOfMonth(1); // like 2023-10-01
        // LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth()); // like 2023-10-31

        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.now();

        List<ExpenseEntity> expenseList = expenseRepository.findAllByProfileIdAndDateBetween(profileEntity.getId(), startDate, endDate);

        return expenseList.stream()
                .map(this::toDTO)
                .toList();
    }

    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) 
    {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        CategoryEntity categoryEntity = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ExpenseEntity newExpense = toEntity(expenseDTO, profileEntity, categoryEntity);
        
        newExpense = expenseRepository.save(newExpense);

        return toDTO(newExpense);
    }

    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) 
    {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        CategoryEntity categoryEntity = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        ExpenseEntity existingExpense = expenseRepository.findByIdAndProfileId(id, profileEntity.getId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if(expenseDTO.getName() != null) existingExpense.setName(expenseDTO.getName());
        if(expenseDTO.getIcon() != null) existingExpense.setIcon(expenseDTO.getIcon());
        if(expenseDTO.getDate() != null) existingExpense.setDate(expenseDTO.getDate());
        if(expenseDTO.getAmount() != null) existingExpense.setAmount(expenseDTO.getAmount());
        if(categoryEntity != null) existingExpense.setCategory(categoryEntity);
        
        existingExpense = expenseRepository.save(existingExpense);

        return toDTO(existingExpense);
    }

    public void deleteExpense(Long id) 
    {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        ExpenseEntity expenseEntity = expenseRepository.findByIdAndProfileId(id, profileEntity.getId())
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if(!profileEntity.getId().equals(expenseEntity.getProfile().getId())) {
            throw new RuntimeException("You do not have permission to delete this expense");
        }

        expenseRepository.delete(expenseEntity);
    }

    public List<ExpenseDTO> getLatest5Expenses() 
    {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        List<ExpenseEntity> expenses = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profileEntity.getId());
        
        return expenses.stream()
                .map(this::toDTO)
                .toList();
    }

    public BigDecimal getTotalExpenses() 
    {
        Long profileId = profileService.getCurrentProfile().getId();
        return expenseRepository.getTotalExpensesByProfileId(profileId);
    }

    public List<ExpenseDTO> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) 
    {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findAllByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profileEntity.getId(), startDate, endDate, keyword, sort);

        return list.stream().map(this::toDTO).toList();
    }

    public List<ExpenseDTO> getExpensesByDate(LocalDate date) 
    {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        List<ExpenseEntity> expenses = expenseRepository.findByProfileIdAndDate(profileEntity.getId(), date);
        
        return expenses.stream().map(this::toDTO).toList();
    }

    // helper methods to convert ExpenseDTO to ExpenseEntity and vice versa
    private ExpenseEntity toEntity(ExpenseDTO expenseDTO, ProfileEntity profileEntity, CategoryEntity categoryEntity) 
    {
        return ExpenseEntity.builder()
                .name(expenseDTO.getName())
                .icon(expenseDTO.getIcon())
                .date(expenseDTO.getDate())
                .amount(expenseDTO.getAmount())
                .createdAt(expenseDTO.getCreatedAt())
                .updatedAt(expenseDTO.getUpdatedAt())
                .profile(profileEntity)
                .category(categoryEntity)
                .build();
    }

    private ExpenseDTO toDTO(ExpenseEntity expenseEntity) 
    {
        return ExpenseDTO.builder()
                .id(expenseEntity.getId())
                .name(expenseEntity.getName())
                .icon(expenseEntity.getIcon())
                .date(expenseEntity.getDate())
                .amount(expenseEntity.getAmount())
                .categoryId(expenseEntity != null ? expenseEntity.getCategory().getId() : null)
                .categoryName(expenseEntity != null ? expenseEntity.getCategory().getName() : "N/A")
                .createdAt(expenseEntity.getCreatedAt())
                .updatedAt(expenseEntity.getUpdatedAt())
                .build();
    }
}
