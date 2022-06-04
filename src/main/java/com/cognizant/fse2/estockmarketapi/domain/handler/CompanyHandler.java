package com.cognizant.fse2.estockmarketapi.domain.handler;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyHandlerPort;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyHandler implements CompanyHandlerPort {

    private final CompanyPersistencePort persistencePort;

    public CompanyHandler(CompanyPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public List<Company> getAll() {
        return persistencePort.findAll();
    }

    @Override
    public Optional<Company> getOne(String companyCode) {
        return persistencePort.findByCode(companyCode);
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
