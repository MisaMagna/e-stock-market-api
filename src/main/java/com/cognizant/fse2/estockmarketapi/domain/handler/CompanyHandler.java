package com.cognizant.fse2.estockmarketapi.domain.handler;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyHandlerPort;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyPersistencePort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyHandler implements CompanyHandlerPort {

    private final CompanyPersistencePort persistencePort;

    public CompanyHandler(CompanyPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public List<Company> getAll() {
        List<Company> companies = persistencePort.findAll();
        for (Company company : companies) {
            List<Stock> todayStocks = company.getStocks()
                    .stream()
                    .filter(stock -> stock.getDate().isEqual(LocalDate.now()))
                    .collect(Collectors.toList());
            company.setStocks(todayStocks);
        }
        return companies;
    }

    @Override
    public Company getOne(String companyCode) {
        Company company = persistencePort.findByCode(companyCode);
        List<Stock> todayStocks = company.getStocks()
                .stream()
                .filter(stock -> stock.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        company.setStocks(todayStocks);
        return company;
    }

    @Override
    public Company create(Company company) {
        return persistencePort.save(company);
    }

    @Override
    public void delete(String companyCode) {
        persistencePort.deleteByCode(companyCode);
    }
}
