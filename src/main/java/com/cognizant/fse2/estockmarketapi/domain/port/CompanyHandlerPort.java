package com.cognizant.fse2.estockmarketapi.domain.port;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface CompanyHandlerPort {
    List<Company> getAll();

    Company getOne(String companyCode);

    Company create(@Valid Company company);

    void delete(String companyCode);
}
