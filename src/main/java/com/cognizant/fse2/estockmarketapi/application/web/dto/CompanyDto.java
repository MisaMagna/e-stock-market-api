package com.cognizant.fse2.estockmarketapi.application.web.dto;

import com.cognizant.fse2.estockmarketapi.domain.model.StockExchange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

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
@Validated
public class CompanyDto {
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String CEO;
    @NotNull
    //@Min(10000000)
    private BigDecimal turnover;
    @NotBlank
    private String website;
    @NotBlank
    private List<StockExchange> exchanges;
    @Valid
    private List<StockDto> stocks;
}
