package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document;

import com.cognizant.fse2.estockmarketapi.domain.model.StockExchange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("companies")
public class CompanyDocument {
    @Id
    private String code;
    private String name;
    private String CEO;
    private BigDecimal turnover;
    private String website;
    private List<StockExchange> exchanges;
    @DBRef
    private List<StockDocument> stocks;
}
