package com.cognizant.fse2.estockmarketapi.domain.handler;

import com.cognizant.fse2.estockmarketapi.domain.DomainTest;
import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Stock Handler")
class StockHandlerTest implements DomainTest {
    @InjectMocks
    private StockHandler handler;

    @Mock
    private StockPersistencePort port;

    @Captor
    private ArgumentCaptor<Stock> captor;

    @BeforeEach
    void setUp() {
        handler = new StockHandler(port);
    }

    @Nested
    @DisplayName("get")
    public class Get {
        @Test
        @DisplayName("should return stocks")
        void shouldReturnStocks() {
            final String companyCode = "bc4b7d";
            Stock stock = Stock.builder()
                    .price(BigDecimal.TEN)
                    .date(LocalDate.now())
                    .time(LocalTime.now())
                    .build();

            given(port.findAllByCompanyCodeAndDateRange(anyString(), any(LocalDate.class), any(LocalDate.class)))
                    .willReturn(List.of(stock, stock, stock));
            assertThat(handler.get(companyCode, LocalDate.now(), LocalDate.now()))
                    .isEqualTo(List.of(stock, stock, stock));
            then(port).should().findAllByCompanyCodeAndDateRange(anyString(), any(LocalDate.class), any(LocalDate.class));
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";

            String message = String.format("Company with id %s does not exists", companyCode);
            willThrow(new CompanyNotFoundException(message)).given(port)
                    .findAllByCompanyCodeAndDateRange(anyString(), any(LocalDate.class), any(LocalDate.class));

            CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class,
                    () -> handler.get(companyCode, LocalDate.now(), LocalDate.now()));

            assertThat(exception.getMessage()).isEqualTo(message);
            then(port).should().findAllByCompanyCodeAndDateRange(anyString(), any(LocalDate.class), any(LocalDate.class));
        }
    }

    @Nested
    @DisplayName("add")
    public class Add {
        @Test
        @DisplayName("should add stock to company")
        void shouldAddStockToCompany() {
            final String companyCode = "bc4b7d";
            Stock stock = Stock.builder()
                    .price(BigDecimal.TEN)
                    .build();

            doNothing().when(port).save(anyString(), captor.capture());
            handler.add(companyCode, stock);

            assertThat(captor.getValue().getDate().getDayOfMonth())
                    .isEqualTo(LocalDate.now().getDayOfMonth());
            assertThat(captor.getValue().getTime().getMinute())
                    .isEqualTo(LocalTime.now().getMinute());

            verify(port, only()).save(anyString(), any(Stock.class));
            then(port).should().save(anyString(), any(Stock.class));
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";
            Stock stock = Stock.builder()
                    .price(BigDecimal.TEN)
                    .build();

            String message = String.format("Company with id %s does not exists", companyCode);
            willThrow(new CompanyNotFoundException(message)).given(port)
                    .save(anyString(), any(Stock.class));

            CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class,
                    () -> handler.add(companyCode, stock));

            assertThat(exception.getMessage()).isEqualTo(message);
            then(port).should().save(anyString(), any(Stock.class));
        }
    }
}