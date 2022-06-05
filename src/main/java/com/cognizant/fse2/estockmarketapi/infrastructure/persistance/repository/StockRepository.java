package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.repository;

import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.StockDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends MongoRepository<StockDocument, String> {
}
