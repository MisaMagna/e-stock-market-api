package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDetailDto;
import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDto;
import com.cognizant.fse2.estockmarketapi.application.web.mapper.CompanyWebMapper;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyHandlerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/market/company")
public class CompanyController {

    private final CompanyHandlerPort handlerPort;

    public CompanyController(CompanyHandlerPort handlerPort) {
        this.handlerPort = handlerPort;
    }

    @GetMapping("/get-all")
    public List<CompanyDto> getAll() {
        List<Company> companies = handlerPort.getAll();
        return CompanyWebMapper.fromDomain(companies);
    }

    // TODO: VALIDATION
    @GetMapping("/info/{companyCode}")
    public CompanyDto getInfo(@PathVariable String companyCode) {
        Company company = handlerPort.getOne(companyCode);
        return CompanyWebMapper.fromDomain(company);
    }

    // TODO: VALIDATION
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto register(@RequestBody CompanyDetailDto detail) {
        Company newCompany = CompanyWebMapper.toDomain(detail);
        Company company = handlerPort.create(newCompany);
        return CompanyWebMapper.fromDomain(company);
    }

    // TODO: VALIDATION
    @DeleteMapping("/delete/{companyCode}")
    public void delete(@PathVariable String companyCode) {
        handlerPort.delete(companyCode);
    }
}
