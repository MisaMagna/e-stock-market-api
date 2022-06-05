package com.cognizant.fse2.estockmarketapi.application.web.mapper;

import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDto;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyWebMapper {

    private CompanyWebMapper() {
    }

    public static Company toDomain(CompanyDto dto) {
        return Company.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .CEO(dto.getCEO())
                .turnover(dto.getTurnover())
                .website(dto.getWebsite())
                .exchanges(dto.getExchanges())
                .stocks(StockWebMapper.toDomain(dto.getStocks()))
                .build();
    }

    public static List<Company> toDomain(List<CompanyDto> dtoList) {
        return dtoList.stream()
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
