package com.cognizant.fse2.estockmarketapi.domain.handler;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyHandlerPort;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyHandler implements CompanyHandlerPort {

    private final CompanyPersistencePort persistencePort;

    public CompanyHandler(CompanyPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public List<Company> getAll() {
        // TODO: RETURN STOCKS OF DAY
        return persistencePort.findAll();
    }

    @Override
    public Company getOne(String companyCode) {
        // TODO: RETURN STOCKS OF DAY
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
