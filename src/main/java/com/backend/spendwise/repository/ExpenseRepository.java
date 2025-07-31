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

import com.backend.spendwise.entity.ExpenseEntity;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long>
{
    // sql query = // SELECT * FROM expenses WHERE id = ?1 AND profile_id = ?2
    Optional<ExpenseEntity> findByIdAndProfileId(Long id, Long profileId);
    
    // sql query = // SELECT * FROM expenses WHERE profile_id = ?1
    List<ExpenseEntity> findAllByProfileId(Long profileId);
    
    // sql query = // SELECT * FROM expenses WHERE category_id = ?1 AND profile_id = ?2
    List<ExpenseEntity> findByCategoryIdAndProfileId(Long categoryId, Long profileId);

    // sql query = // SELECT * FROM expenses WHERE profile_id = ?1 AND date BETWEEN ?2 AND ?3
    List<ExpenseEntity> findAllByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

    // sql query = // SELECT * FROM expenses WHERE profile_id = ?1 ORDER BY date DESC LIMIT 5
    List<ExpenseEntity> findTop5ByProfileIdOrderByDateDesc(Long profileId);

    // sql query = // SELECT COALESCE(SUM(amount), 0) FROM expenses WHERE profile_id = :profileId
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM ExpenseEntity e WHERE e.profile.id = :profileId") // this will give us the total expenses for a profile 
    BigDecimal getTotalExpensesByProfileId(@Param("profileId") Long profileId);

    // sql query = // SELECT * FROM expenses WHERE profile_id = ?1 AND date BETWEEN ?2 AND ?3 AND name LIKE %?4%
    List<ExpenseEntity> findAllByProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long profileId, LocalDate startDate, LocalDate endDate, String keyword, Sort sort);

}
