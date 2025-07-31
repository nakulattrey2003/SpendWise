package com.backend.spendwise.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterDTO 
{
    private String type;  // "income" or "expense"
    private LocalDate startDate;
    private LocalDate endDate;
    private String keyword;
    private String sortField; //this is for on which base we have to sort -- date, amount, name
    private String sortOrder; //asc or desc
}
