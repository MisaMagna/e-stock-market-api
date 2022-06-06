package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Validated
public interface StockPersistencePort {
    List<Stock> findAllByCompanyCodeAndDateRange(String companyCode, LocalDate startDate, LocalDate endDate);

    void save(String companyCode, @Valid Stock stock);
}
