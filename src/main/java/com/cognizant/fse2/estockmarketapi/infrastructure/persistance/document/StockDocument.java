package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("stocks")
public class StockDocument {
    @Id
    private String id;
    private BigDecimal price;
    private LocalDate date;
    private LocalTime time;
}
