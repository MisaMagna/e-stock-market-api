package com.cognizant.fse2.estockmarketapi.application.web.dto;

import com.cognizant.fse2.estockmarketapi.domain.model.StockExchange;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CompanyDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;
    private String name;
    @JsonProperty("CEO")
    private String CEO;
    private BigDecimal turnover;
    private String website;
    private List<StockExchange> exchanges;
    private List<StockDto> stocks;
}
