package com.cognizant.fse2.estockmarketapi.application.web.mapper;

import com.cognizant.fse2.estockmarketapi.application.web.dto.StockDto;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;

import java.util.List;
import java.util.stream.Collectors;

public abstract class StockWebMapper {

    public static Stock toDomain(StockDto dto) {
        return Stock.builder()
                .price(dto.getPrice())
                .date(dto.getDate())
                .time(dto.getTime())
                .build();
    }

    public static List<Stock> toDomain(List<StockDto> dtoList) {
        return dtoList.stream()
                .map(StockWebMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static StockDto fromDomain(Stock stock) {
        return StockDto.builder()
                .price(stock.getPrice())
                .date(stock.getDate())
                .time(stock.getTime())
                .build();
    }

    public static List<StockDto> fromDomain(List<Stock> stocks) {
        return stocks.stream()
                .map(StockWebMapper::fromDomain)
                .collect(Collectors.toList());
    }
}
