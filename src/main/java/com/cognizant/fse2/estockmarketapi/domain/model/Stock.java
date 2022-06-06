package com.cognizant.fse2.estockmarketapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {
    // TODO: LOOK IF NEED TO MOVE VALIDATION LOGIC TO HERE
    private BigDecimal price;
    private LocalDate date;
    private LocalTime time;
}
