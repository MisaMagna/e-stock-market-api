package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDto;
import com.cognizant.fse2.estockmarketapi.application.web.mapper.CompanyWebMapper;
import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyHandlerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/info/{companyCode}")
    public Optional<CompanyDto> getInfo(@PathVariable String companyCode) {
        Company company = handlerPort.getOne(companyCode);
        return Optional.of(CompanyWebMapper.fromDomain(company));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public CompanyDto register(@RequestBody CompanyDto companyDto) {
        Company newCompany = CompanyWebMapper.toDomain(companyDto);
        Company company = handlerPort.create(newCompany);
        return CompanyWebMapper.fromDomain(newCompany);
    }

    @DeleteMapping("/delete/{companyCode}")
    public void delete(@PathVariable String companyCode) {
        handlerPort.delete(companyCode);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> companyNotFoundExceptionHandler(CompanyNotFoundException exception) {
        return Map.of("Status", 404, "error", exception.getMessage());
    }
}
