package com.backend.spendwise.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.spendwise.dto.IncomeDTO;
import com.backend.spendwise.entity.CategoryEntity;
import com.backend.spendwise.entity.IncomeEntity;
import com.backend.spendwise.entity.ProfileEntity;
import com.backend.spendwise.repository.CategoryRepository;
import com.backend.spendwise.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final ProfileService profileService;
    private final CategoryRepository categoryRepository;

    public List<IncomeDTO> readIncomesForCurrentMonth() {
        ProfileEntity profileEntity = profileService.getCurrentProfile();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        List<IncomeEntity> incomeList = incomeRepository.findAllByProfileIdAndDateBetween(profileEntity.getId(), startDate, endDate);

        return incomeList.stream()
                .map(this::toDTO)
                .toList();
    }

    public IncomeDTO addIncome(IncomeDTO incomeDTO) {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        CategoryEntity categoryEntity = categoryRepository.findById(incomeDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        IncomeEntity newIncome = toEntity(incomeDTO, profileEntity, categoryEntity);

        newIncome = incomeRepository.save(newIncome);

        return toDTO(newIncome);
    }

    public IncomeDTO updateIncome(Long id, IncomeDTO incomeDTO) {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        CategoryEntity categoryEntity = categoryRepository.findById(incomeDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        IncomeEntity existingIncome = incomeRepository.findByIdAndProfileId(id, profileEntity.getId())
                .orElseThrow(() -> new RuntimeException("Income not found"));

        if (incomeDTO.getName() != null) existingIncome.setName(incomeDTO.getName());
        if (incomeDTO.getIcon() != null) existingIncome.setIcon(incomeDTO.getIcon());
        if (incomeDTO.getDate() != null) existingIncome.setDate(incomeDTO.getDate());
        if (incomeDTO.getAmount() != null) existingIncome.setAmount(incomeDTO.getAmount());
        if (categoryEntity != null) existingIncome.setCategory(categoryEntity);

        existingIncome = incomeRepository.save(existingIncome);

        return toDTO(existingIncome);
    }

    public void deleteIncome(Long id) {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        IncomeEntity incomeEntity = incomeRepository.findByIdAndProfileId(id, profileEntity.getId())
                .orElseThrow(() -> new RuntimeException("Income not found"));

        if (!profileEntity.getId().equals(incomeEntity.getProfile().getId())) {
            throw new RuntimeException("You do not have permission to delete this income");
        }

        incomeRepository.delete(incomeEntity);
    }

    public List<IncomeDTO> getLatest5Incomes() {
        ProfileEntity profileEntity = profileService.getCurrentProfile();
        List<IncomeEntity> incomes = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profileEntity.getId());

        return incomes.stream()
                .map(this::toDTO)
                .toList();
    }

    public BigDecimal getTotalIncome() {
        Long profileId = profileService.getCurrentProfile().getId();
        return incomeRepository.getTotalIncomeByProfileId(profileId);
    }

    // helper methods to convert DTO to Entity and vice versa
    private IncomeEntity toEntity(IncomeDTO incomeDTO, ProfileEntity profileEntity, CategoryEntity categoryEntity) {
        return IncomeEntity.builder()
                .name(incomeDTO.getName())
                .icon(incomeDTO.getIcon())
                .date(incomeDTO.getDate())
                .amount(incomeDTO.getAmount())
                .createdAt(incomeDTO.getCreatedAt())
                .updatedAt(incomeDTO.getUpdatedAt())
                .profile(profileEntity)
                .category(categoryEntity)
                .build();
    }

    private IncomeDTO toDTO(IncomeEntity incomeEntity) {
        return IncomeDTO.builder()
                .id(incomeEntity.getId())
                .name(incomeEntity.getName())
                .icon(incomeEntity.getIcon())
                .date(incomeEntity.getDate())
                .amount(incomeEntity.getAmount())
                .categoryId(incomeEntity != null ? incomeEntity.getCategory().getId() : null)
                .categoryName(incomeEntity != null ? incomeEntity.getCategory().getName() : "N/A")
                .createdAt(incomeEntity.getCreatedAt())
                .updatedAt(incomeEntity.getUpdatedAt())
                .build();
    }
}
