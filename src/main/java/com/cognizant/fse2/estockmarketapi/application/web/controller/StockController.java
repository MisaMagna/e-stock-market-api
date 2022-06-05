package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.application.web.dto.StockDto;
import com.cognizant.fse2.estockmarketapi.application.web.mapper.StockWebMapper;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockHandlerPort;
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
    public List<StockDto> getBy(@PathVariable String companyCode, LocalDate startDate, LocalDate endDate) {
        List<Stock> stocks = handlerPort.get(companyCode, startDate, endDate);
        return StockWebMapper.fromDomain(stocks);
    }

    @PostMapping("/add/{companyCode}")
    @ResponseStatus(HttpStatus.OK)
    public void add(@PathVariable String companyCode, @RequestBody StockDto stockDto) {
        Stock stock = StockWebMapper.toDomain(stockDto);
        handlerPort.add(companyCode, stock);
    }
}
