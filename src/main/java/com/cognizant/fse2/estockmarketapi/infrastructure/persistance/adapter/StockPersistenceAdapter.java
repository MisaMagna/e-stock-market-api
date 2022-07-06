package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.adapter;

import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockPersistencePort;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.CompanyDocument;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class StockPersistenceAdapter implements StockPersistencePort {

    private final CompanyRepository repository;

    public StockPersistenceAdapter(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Stock> findAllByCompanyCodeAndDateRange(String companyCode, LocalDate startDate, LocalDate endDate) {
        Optional<CompanyDocument> document = repository.findById(companyCode);
        if (document.isEmpty()) {
            String message = String.format("Company with id %s does not exists", companyCode);
            log.error(message);
            throw new CompanyNotFoundException(message);
        }
        return document.get().getStocks().stream()
                .filter(stock -> (stock.getDate().isEqual(startDate) || stock.getDate().isAfter(startDate))
                        && (stock.getDate().isEqual(endDate) || stock.getDate().isBefore(endDate)))
                .collect(Collectors.toList());
    }

    @Override
    public void save(String companyCode, Stock stock) {
        Optional<CompanyDocument> document = repository.findById(companyCode);
        if (document.isEmpty()) {
            String message = String.format("Company with id %s does not exists", companyCode);
            log.error(message);
            throw new CompanyNotFoundException(message);
        }
        document.get().getStocks().add(stock);
        repository.save(document.get());
    }
}
