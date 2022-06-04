package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.mapper;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.CompanyDocument;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CompanyPersistenceMapper {
    CompanyDocument fromDomain(Company company);

    Company toDomain(CompanyDocument document);

    List<CompanyDocument> fromDomain(List<Company> companies);

    List<Company> toDomain(List<CompanyDocument> documents);
}
