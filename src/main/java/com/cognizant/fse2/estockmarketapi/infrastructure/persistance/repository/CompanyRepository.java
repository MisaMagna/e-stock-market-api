package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.repository;

import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.CompanyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<CompanyDocument, String> {
}
