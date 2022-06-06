package com.cognizant.fse2.estockmarketapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    // TODO: LOOK IF NEED TO MOVE VALIDATION LOGIC TO HERE
    private String code;
    private String name;
    private String CEO;
    private BigDecimal turnover;
    private String website;
    private List<StockExchange> exchanges;
    private List<Stock> stocks;
}
