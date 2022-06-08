package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.application.web.dto.StockDto;
import com.cognizant.fse2.estockmarketapi.application.web.dto.StockPriceDto;
import com.cognizant.fse2.estockmarketapi.application.web.mapper.StockWebMapper;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockHandlerPort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/market/stock")
public class StockController {

    private final StockHandlerPort handlerPort;

    public StockController(StockHandlerPort handlerPort) {
        this.handlerPort = handlerPort;
    }

    @GetMapping("/get/{companyCode}/{startDate}/{endDate}")
    public List<StockDto> getBy(@PathVariable String companyCode,
                                @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Stock> stocks = handlerPort.get(companyCode, startDate, endDate);
        return StockWebMapper.fromDomain(stocks);
    }

    @PostMapping("/add/{companyCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@PathVariable String companyCode, @RequestBody StockPriceDto price) {
        Stock stock = StockWebMapper.toDomain(price);
        handlerPort.add(companyCode, stock);
    }
}
