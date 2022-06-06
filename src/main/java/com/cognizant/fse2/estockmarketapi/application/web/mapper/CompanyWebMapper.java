package com.cognizant.fse2.estockmarketapi.application.web.mapper;

import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDetailDto;
import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDto;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CompanyWebMapper {

    public static Company toDomain(CompanyDetailDto detail) {
        return Company.builder()
                .name(detail.getName())
                .CEO(detail.getCEO())
                .turnover(detail.getTurnover())
                .website(detail.getWebsite())
                .exchanges(detail.getExchanges())
                .stocks(Collections.emptyList())
                .build();
    }

    public static List<Company> toDomain(List<CompanyDetailDto> details) {
        return details.stream()
                .map(CompanyWebMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static CompanyDto fromDomain(Company company) {
        return CompanyDto.builder()
                .code(company.getCode())
                .name(company.getName())
                .CEO(company.getCEO())
                .turnover(company.getTurnover())
                .website(company.getWebsite())
                .exchanges(company.getExchanges())
                .stocks(StockWebMapper.fromDomain(company.getStocks()))
                .build();
    }

    public static List<CompanyDto> fromDomain(List<Company> companies) {
        return companies.stream()
                .map(CompanyWebMapper::fromDomain)
                .collect(Collectors.toList());
    }
}
