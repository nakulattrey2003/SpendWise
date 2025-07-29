package com.backend.spendwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.spendwise.entity.IncomeEntity;

@Repository
public interface ExpenseRepository extends JpaRepository<IncomeEntity, Long>
{
    
}
