package com.cognizant.fse2.estockmarketapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String CEO;
    @NotNull
    @Min(10000000)
    private BigDecimal turnover;
    @NotBlank
    private String website;
    @NotBlank
    private List<StockExchange> exchanges;
    @Valid
    private List<Stock> stocks;
}
