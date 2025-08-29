package com.backend.spendwise.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.spendwise.dto.IncomeDTO;
import com.backend.spendwise.service.IncomeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @GetMapping("/ping")
public ResponseEntity<String> ping() {
    log.info("Ping endpoint hit!");
    return ResponseEntity.ok("pong");
}

    @GetMapping("/getAllIncomesForCurrentMonth")
    // public ResponseEntity<List<IncomeDTO>> getAllIncomesForCurrentMonth() {
    //     List<IncomeDTO> incomes = incomeService.readIncomesForCurrentMonth();
    //     // if (incomes == null || incomes.isEmpty()) {
    //     //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     // }
    //     return ResponseEntity.ok(incomes);
    // }
    public ResponseEntity<List<IncomeDTO>> getAllIncomesForCurrentMonth() {
            log.info("Fetching all incomes for current month...");

            List<IncomeDTO> incomes = incomeService.readIncomesForCurrentMonth();

            if (incomes == null || incomes.isEmpty()) {
                log.warn("No incomes found for current month");
                return ResponseEntity.ok().body(incomes); // will return []
            }

            log.info("Found {} incomes for current month", incomes.size());
            return ResponseEntity.ok(incomes);
        }

    @PostMapping("/addIncome")
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO incomeDTO) {
        IncomeDTO savedIncome = incomeService.addIncome(incomeDTO);
        if (savedIncome == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIncome);
    }

    @DeleteMapping("/deleteIncome/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        try {
            incomeService.deleteIncome(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getLatest5Incomes")
    public ResponseEntity<List<IncomeDTO>> getLatest5Incomes() {
        List<IncomeDTO> latestIncomes = incomeService.getLatest5Incomes();
        if (latestIncomes == null || latestIncomes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(latestIncomes);
    }

    @PostMapping("/updateIncome/{id}")
    public ResponseEntity<IncomeDTO> updateIncome(@PathVariable Long id, @RequestBody IncomeDTO incomeDTO) {
        try {
            IncomeDTO updatedIncome = incomeService.updateIncome(id, incomeDTO);
            return ResponseEntity.ok(updatedIncome);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getTotalIncome")
    public ResponseEntity<BigDecimal> getTotalIncome() {
        BigDecimal totalIncome = incomeService.getTotalIncome();
        if (totalIncome == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(totalIncome);
    }
}
