package com.cognizant.fse2.estockmarketapi.domain.handler;

import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockHandlerPort;
import com.cognizant.fse2.estockmarketapi.domain.port.StockPersistencePort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockHandler implements StockHandlerPort {

    private final StockPersistencePort persistencePort;

    public StockHandler(StockPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public List<Stock> get(String companyCode, LocalDate startDate, LocalDate endDate) {
        return persistencePort.findAllByCompanyCodeAndDateRange(companyCode, startDate, endDate)
                .stream()
                .sorted(Comparator.comparing(Stock::getDate).thenComparing(Stock::getTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void add(String companyCode, Stock stock) {
        stock.setDate(LocalDate.now());
        stock.setTime(LocalTime.now());
        persistencePort.save(companyCode, stock);
    }
}
