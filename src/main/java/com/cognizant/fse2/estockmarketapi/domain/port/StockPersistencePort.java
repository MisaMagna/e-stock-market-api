package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.model.Stock;

import java.time.LocalDate;
import java.util.List;

public interface StockPersistencePort {
    List<Stock> findAllByCompanyCodeAndDateRange(String companyCode, LocalDate startDate, LocalDate endDate);

    void save(String companyCode, Stock stock);
}
