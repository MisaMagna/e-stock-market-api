package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;

import java.util.List;

public interface CompanyHandlerPort {
    List<Company> getAll();

    Company getOne(String companyCode);

    Company create(Company company);

    void delete(String companyCode);
}
