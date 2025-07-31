package com.backend.spendwise.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.spendwise.entity.IncomeEntity;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    // SELECT * FROM incomes WHERE id = ?1 AND profile_id = ?2
    Optional<IncomeEntity> findByIdAndProfileId(Long id, Long profileId);

    // SELECT * FROM incomes WHERE profile_id = ?1
    List<IncomeEntity> findAllByProfileId(Long profileId);

    // SELECT * FROM incomes WHERE category_id = ?1 AND profile_id = ?2
    List<IncomeEntity> findByCategoryIdAndProfileId(Long categoryId, Long profileId);

    // SELECT * FROM incomes WHERE profile_id = ?1 AND date BETWEEN ?2 AND ?3
    List<IncomeEntity> findAllByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

    // SELECT * FROM incomes WHERE profile_id = ?1 ORDER BY date DESC LIMIT 5
    List<IncomeEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

    // SELECT COALESCE(SUM(amount), 0) FROM incomes WHERE profile_id = :profileId
    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM IncomeEntity i WHERE i.profile.id = :profileId")
    BigDecimal getTotalIncomeByProfileId(@Param("profileId") Long profileId);

    // SELECT * FROM incomes WHERE profile_id = ?1 AND date BETWEEN ?2 AND ?3 AND name LIKE %?4%
    List<IncomeEntity> findAllByProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long profileId, LocalDate startDate, LocalDate endDate, String keyword, Sort sort);
}
