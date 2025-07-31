package com.backend.spendwise.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.backend.spendwise.dto.ExpenseDTO;
import com.backend.spendwise.dto.IncomeDTO;
import com.backend.spendwise.dto.RecentTransactionDTO;
import com.backend.spendwise.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProfileService profileService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public Map<String, Object> getDashboardData() {
        ProfileEntity profile = profileService.getCurrentProfile();
        Map<String, Object> returnValue = new LinkedHashMap<>();

        List<IncomeDTO> latestIncomes = incomeService.getLatest5Incomes();
        List<ExpenseDTO> latestExpenses = expenseService.getLatest5Expenses();

        // Combine incomes and expenses into a single list of RecentTransactionDTO
        // We create RecentTransactionDTO so incomes and expenses share the same structure with a type field, making it easy to merge, sort, and send as one list to the frontend.
        List<RecentTransactionDTO> concat = Stream.concat(
                latestIncomes.stream().map(income ->
                        RecentTransactionDTO.builder()
                                .id(income.getId())
                                .profileId(profile.getId())
                                .icon(income.getIcon())
                                .name(income.getName())
                                .amount(income.getAmount())
                                .date(income.getDate())
                                .createdAt(income.getCreatedAt())
                                .updatedAt(income.getUpdatedAt())
                                .type("income")
                                .build()
                ),
                latestExpenses.stream().map(expense ->
                        RecentTransactionDTO.builder()
                                .id(expense.getId())
                                .profileId(profile.getId())
                                .icon(expense.getIcon())
                                .name(expense.getName())
                                .amount(expense.getAmount())
                                .date(expense.getDate())
                                .createdAt(expense.getCreatedAt())
                                .updatedAt(expense.getUpdatedAt())
                                .type("expense")
                                .build()
                )
        )
        .sorted((a, b) -> {
            int cmp = b.getDate().compareTo(a.getDate());
            if (cmp == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null) {
                return b.getCreatedAt().compareTo(a.getCreatedAt());
            }
            return cmp;
        })
        .collect(Collectors.toList());

        // Put data into the map
        returnValue.put("totalBalance", incomeService.getTotalIncome().subtract(expenseService.getTotalExpenses()));
        returnValue.put("totalIncome", incomeService.getTotalIncome());
        returnValue.put("totalExpense", expenseService.getTotalExpenses());
        returnValue.put("recent5Expenses", latestExpenses);
        returnValue.put("recent5Incomes", latestIncomes);
        returnValue.put("recentTransactions", concat);

        return returnValue;
    }
}


// example output
// {
//   "totalBalance": 54000,
//   "totalIncome": 80000,
//   "totalExpense": 26000,
//   "recent5Expenses": [
//     { "id": 21, "name": "Groceries", "amount": 5000, "date": "2025-07-29" },
//     { "id": 18, "name": "Fuel", "amount": 2000, "date": "2025-07-27" }
//   ],
//   "recent5Incomes": [
//     { "id": 10, "name": "Salary", "amount": 60000, "date": "2025-07-25" }
//   ],
//   "recentTransactions": [
//     { "id": 10, "type": "income", "name": "Salary", "amount": 60000, "date": "2025-07-25" },
//     { "id": 21, "type": "expense", "name": "Groceries", "amount": 5000, "date": "2025-07-29" }
//   ]
// }
