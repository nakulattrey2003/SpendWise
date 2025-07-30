package com.backend.spendwise.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.spendwise.dto.ExpenseDTO;
import com.backend.spendwise.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController 
{
    private final ExpenseService expenseService;
    
    @GetMapping("/getAllExpensesForCurrentMonth")
    public ResponseEntity<List<ExpenseDTO>> getMethodName() 
    {
        List<ExpenseDTO> expenses = expenseService.readExpensesForCurrentMonth();
        if (expenses == null || expenses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(expenses);
    }
    
    @PostMapping("/addExpense")
    public ResponseEntity<ExpenseDTO> postMethodName(@RequestBody ExpenseDTO expenseDTO) 
    {
        ExpenseDTO savedExpense = expenseService.addExpense(expenseDTO);
        if (savedExpense == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }
    
    @DeleteMapping("/deleteExpense/{id}")
    public ResponseEntity<Void> deleteMethodName(@PathVariable Long id)
    {
        try 
        {
            expenseService.deleteExpense(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getLatest5Expenses")
    public ResponseEntity<List<ExpenseDTO>> getLatest5Expenses()
    {
        List<ExpenseDTO> latestExpenses = expenseService.getLatest5Expenses();
        if (latestExpenses == null || latestExpenses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(latestExpenses);
    }

    @PostMapping("/updateExpense/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO) 
    {
        try 
        {
            ExpenseDTO updatedExpense = expenseService.updateExpense(id, expenseDTO);
            return ResponseEntity.ok(updatedExpense);
        } 
        catch (RuntimeException e) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getTotalExpenses")
    public ResponseEntity<BigDecimal> getTotalExpenses()
    {
        BigDecimal totalExpenses = expenseService.getTotalExpenses();
        if (totalExpenses == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(totalExpenses);
    }

}