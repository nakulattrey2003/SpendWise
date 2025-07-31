package com.backend.spendwise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.spendwise.dto.FilterDTO;
import com.backend.spendwise.service.ExpenseService;
import com.backend.spendwise.service.IncomeService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController 
{
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> filterTransactions(@RequestBody FilterDTO filterDTO) 
    {
        LocalDate startDate = filterDTO.getStartDate() != null ? filterDTO.getStartDate() : LocalDate.now().minusYears(10);;
        LocalDate endDate = filterDTO.getEndDate() != null ? filterDTO.getEndDate() : LocalDate.now();
        String keyword = filterDTO.getKeyword() != null ? filterDTO.getKeyword() : "";
        String sortField = filterDTO.getSortField() != null ? filterDTO.getSortField() : "date";
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(filterDTO.getSortOrder())
        ? Sort.Direction.DESC
        : Sort.Direction.ASC;

        Sort sort = Sort.by(sortDirection, sortField);

        if(filterDTO.getType().equalsIgnoreCase("income")) return ResponseEntity.ok(incomeService.filterIncomes(startDate, endDate, keyword, sort));
        else if(filterDTO.getType().equalsIgnoreCase("expense")) return ResponseEntity.ok(expenseService.filterExpenses(startDate, endDate, keyword, sort));
        else return ResponseEntity.badRequest().body("Invalid type specified");
    }
}
