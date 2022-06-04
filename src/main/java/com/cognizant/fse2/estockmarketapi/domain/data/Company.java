package com.cognizant.fse2.estockmarketapi.domain.data;

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
    private String code;
    private String name;
    private String CEO;
    private BigDecimal turnover;
    private String website;
    private List<StockExchange> exchanges;
    private List<Stock> stocks;
}
