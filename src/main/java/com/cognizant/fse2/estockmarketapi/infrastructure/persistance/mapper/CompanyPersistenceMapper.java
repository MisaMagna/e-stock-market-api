package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.mapper;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.CompanyDocument;

import java.util.List;
import java.util.stream.Collectors;

public abstract class CompanyPersistenceMapper {

    public static Company toDomain(CompanyDocument document) {
        return Company.builder()
                .code(document.getCode())
                .name(document.getName())
                .CEO(document.getCEO())
                .turnover(document.getTurnover())
                .website(document.getWebsite())
                .exchanges(document.getExchanges())
                .stocks(StockPersistenceMapper.toDomain(document.getStocks()))
                .build();
    }

    public static List<Company> toDomain(List<CompanyDocument> documents) {
        return documents.stream()
                .map(CompanyPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static CompanyDocument fromDomain(Company company) {
        return CompanyDocument.builder()
                .code(company.getCode())
                .name(company.getName())
                .CEO(company.getCEO())
                .turnover(company.getTurnover())
                .website(company.getWebsite())
                .exchanges(company.getExchanges())
                .stocks(StockPersistenceMapper.fromDomain(company.getStocks()))
                .build();
    }

    public static List<CompanyDocument> fromDomain(List<Company> companies) {
        return companies.stream()
                .map(CompanyPersistenceMapper::fromDomain)
                .collect(Collectors.toList());
    }
}
