package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.adapter;

import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyPersistencePort;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.mapper.CompanyPersistenceMapper;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.CompanyDocument;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyPersistenceAdapter implements CompanyPersistencePort {

    private final CompanyRepository repository;
    private final CompanyPersistenceMapper mapper;

    public CompanyPersistenceAdapter(CompanyRepository repository, CompanyPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Company> findAll() {
        List<CompanyDocument> documents = repository.findAll();
        return mapper.toDomain(documents);
    }

    @Override
    public Optional<Company> findByCode(String companyCode) {
        // TODO: Throw Exceptions
        Optional<CompanyDocument> document = repository.findById(companyCode);
        Company company = mapper.toDomain(document.get());
        return Optional.of(company);
    }

    @Override
    public Company save(Company company) {
        CompanyDocument document = repository.save(mapper.fromDomain(company));
        return mapper.toDomain(document);
    }

    @Override
    public void deleteByCode(String companyCode) {
        repository.deleteById(companyCode);
    }
}
