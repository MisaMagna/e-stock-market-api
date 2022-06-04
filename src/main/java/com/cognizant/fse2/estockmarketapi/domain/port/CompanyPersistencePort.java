package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.data.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyPersistencePort {
    List<Company> findAll();

    Optional<Company> findByCode(String companyCode);

    Company save(Company company);

    void deleteByCode(String companyCode);
}
