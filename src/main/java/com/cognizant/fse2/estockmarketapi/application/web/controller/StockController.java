package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.application.web.dto.StockDto;
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
        return null;
    }

    @PostMapping("/add/{companyCode}")
    @ResponseStatus(HttpStatus.OK)
    public StockDto add(@PathVariable String companyCode, @RequestBody StockDto stockDto) {
        return null;
    }
}
