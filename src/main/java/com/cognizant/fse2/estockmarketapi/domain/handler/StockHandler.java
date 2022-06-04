package com.cognizant.fse2.estockmarketapi.domain.handler;

import com.cognizant.fse2.estockmarketapi.domain.data.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockHandlerPort;
import com.cognizant.fse2.estockmarketapi.domain.port.StockPersistencePort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockHandler implements StockHandlerPort {

    private final StockPersistencePort persistencePort;

    public StockHandler(StockPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public List<Stock> get(String companyCode, LocalDate startDate, LocalDate endDate) {
        return persistencePort.findAllByCompanyCodeAndDateRange(companyCode, startDate, endDate);
    }

    @Override
    public Stock add(String companyCode, Stock stock) {
        return persistencePort.save(companyCode, stock);
    }
}
