package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.data.Stock;

import java.time.LocalDate;
import java.util.List;

public interface StockHandlerPort {
    List<Stock> get(String companyCode, LocalDate startDate, LocalDate endDate);

    Stock add(String companyCode, Stock stock);
}
