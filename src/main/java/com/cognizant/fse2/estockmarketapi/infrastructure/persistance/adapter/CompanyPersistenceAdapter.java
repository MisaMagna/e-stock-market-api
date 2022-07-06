package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.adapter;

import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyPersistencePort;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.CompanyDocument;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.mapper.CompanyPersistenceMapper;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.repository.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CompanyPersistenceAdapter implements CompanyPersistencePort {

    private final CompanyRepository repository;

    public CompanyPersistenceAdapter(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Company> findAll() {
        List<CompanyDocument> documents = repository.findAll();
        return CompanyPersistenceMapper.toDomain(documents);
    }

    @Override
    public Company findByCode(String companyCode) {
        Optional<CompanyDocument> document = repository.findById(companyCode);
        if (document.isEmpty()) {
            String message = String.format("Company with id %s does not exists", companyCode);
            log.error(message);
            throw new CompanyNotFoundException(message);
        }
        return CompanyPersistenceMapper.toDomain(document.get());
    }

    @Override
    public Company save(Company company) {
        CompanyDocument document = repository.save(CompanyPersistenceMapper.fromDomain(company));
        return CompanyPersistenceMapper.toDomain(document);
    }

    @Override
    public void deleteByCode(String companyCode) {
        Optional<CompanyDocument> document = repository.findById(companyCode);
        if (document.isEmpty()) {
            String message = String.format("Company with id %s does not exists", companyCode);
            log.error(message);
            throw new CompanyNotFoundException(message);
        }
        repository.deleteById(companyCode);
    }
}
