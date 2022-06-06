package com.cognizant.fse2.estockmarketapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    private String code;
    @NotEmpty
    private String name;
    @NotEmpty
    private String CEO;
    @NotNull
    @Min(10000000)
    private BigDecimal turnover;
    @NotEmpty
    private String website;
    @NotEmpty
    private List<StockExchange> exchanges;
    @Valid
    private List<Stock> stocks;
}
