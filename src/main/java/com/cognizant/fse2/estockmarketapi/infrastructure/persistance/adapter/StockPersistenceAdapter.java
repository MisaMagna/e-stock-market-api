package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.adapter;

import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockPersistencePort;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.mapper.StockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockPersistenceAdapter implements StockPersistencePort {

    private final StockRepository repository;

    public StockPersistenceAdapter(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Stock> findAllByCompanyCodeAndDateRange(String companyCode, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Stock save(String companyCode, Stock stock) {
        return null;
    }
}
