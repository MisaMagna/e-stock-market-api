package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.mapper;

import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.StockDocument;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StockPersistenceMapper {

    public static Stock toDomain(StockDocument document) {
        return Stock.builder()
                .price(document.getPrice())
                .date(document.getDate())
                .time(document.getTime())
                .build();
    }

    public static List<Stock> toDomain(List<StockDocument> documents) {
        return documents.stream()
                .map(StockPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static StockDocument fromDomain(Stock stock) {
        return StockDocument.builder()
                .price(stock.getPrice())
                .date(stock.getDate())
                .time(stock.getTime())
                .build();
    }

    public static List<StockDocument> fromDomain(List<Stock> stocks) {
        return stocks.stream()
                .map(StockPersistenceMapper::fromDomain)
                .collect(Collectors.toList());
    }
}
