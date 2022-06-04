package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.mapper;

import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.StockDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<StockDocument, String> {
}
