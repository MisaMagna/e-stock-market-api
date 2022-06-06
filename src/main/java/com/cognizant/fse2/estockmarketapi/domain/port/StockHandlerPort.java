package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Validated
public interface StockHandlerPort {
    List<Stock> get(String companyCode, LocalDate startDate, LocalDate endDate);

    void add(String companyCode, @Valid Stock stock);
}
