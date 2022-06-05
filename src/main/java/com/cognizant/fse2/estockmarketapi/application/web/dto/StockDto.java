package com.cognizant.fse2.estockmarketapi.application.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class StockDto {
    @NotNull
    //@Min(10000)
    private BigDecimal price;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
}
