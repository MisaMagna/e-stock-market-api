package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDetailDto;
import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDto;
import com.cognizant.fse2.estockmarketapi.application.web.mapper.CompanyWebMapper;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyHandlerPort;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1.0/market/company")
public class CompanyController {

    private final CompanyHandlerPort handlerPort;

    public CompanyController(CompanyHandlerPort handlerPort) {
        this.handlerPort = handlerPort;
    }

    @GetMapping("/get-all")
    public List<CompanyDto> getAll() {
        List<Company> companies = handlerPort.getAll();
        log.info("Retrieved all companies");
        return CompanyWebMapper.fromDomain(companies);
    }

    @GetMapping("/info/{companyCode}")
    public CompanyDto getInfo(@PathVariable String companyCode) {
        Company company = handlerPort.getOne(companyCode);
        log.info("Retrieved a company with code: {}", companyCode);
        return CompanyWebMapper.fromDomain(company);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDto register(@RequestBody CompanyDetailDto detail) {
        Company newCompany = CompanyWebMapper.toDomain(detail);
        Company company = handlerPort.create(newCompany);
        log.info("Created a company with data: {}", detail);
        return CompanyWebMapper.fromDomain(company);
    }

    @DeleteMapping("/delete/{companyCode}")
    public void delete(@PathVariable String companyCode) {
        handlerPort.delete(companyCode);
        log.info("Deleted a company with code: {}", companyCode);
    }
}
