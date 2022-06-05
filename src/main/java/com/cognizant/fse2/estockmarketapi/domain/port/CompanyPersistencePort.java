package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;

import java.util.List;

public interface CompanyPersistencePort {
    List<Company> findAll();

    Company findByCode(String companyCode);

    Company save(Company company);

    void deleteByCode(String companyCode);
}
