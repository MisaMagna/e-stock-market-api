package com.cognizant.fse2.estockmarketapi.application.web.mapper;

import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDto;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyWebMapper {
    CompanyDto fromDomain(Company company);

    Company toDomain(CompanyDto document);

    List<CompanyDto> fromDomain(List<Company> companies);

    List<Company> toDomain(List<CompanyDto> documents);
}
