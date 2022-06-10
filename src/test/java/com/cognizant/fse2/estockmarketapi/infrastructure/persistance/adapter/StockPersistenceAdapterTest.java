package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.adapter;

import com.cognizant.fse2.estockmarketapi.AppTest;
import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.PersistenceTest;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.document.CompanyDocument;
import com.cognizant.fse2.estockmarketapi.infrastructure.persistance.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Stock Persistence Adapter")
class StockPersistenceAdapterTest implements PersistenceTest, AppTest {

    @InjectMocks
    private StockPersistenceAdapter adapter;

    @Mock
    private CompanyRepository repository;

    @BeforeEach
    void setUp() {
        adapter = new StockPersistenceAdapter(repository);
    }

    @Nested
    @DisplayName("findAllByCompanyCodeAndDateRange")
    public class FindAllByCompanyCodeAndDateRange {
        @Test
        @DisplayName("should return stocks in date range")
        void shouldReturnStocksInDateRange() {
            final String companyCode = "bc4b7d";
            Stock todayStock = Stock.builder()
                    .date(LocalDate.now())
                    .build();

            Stock yesterdayStock = Stock.builder()
                    .date(LocalDate.now().minusDays(1))
                    .build();

            CompanyDocument document = CompanyDocument.builder()
                    .code(companyCode)
                    .stocks(List.of(todayStock, yesterdayStock))
                    .build();


            given(repository.findById(anyString())).willReturn(Optional.of(document));
            assertThat(adapter.findAllByCompanyCodeAndDateRange(companyCode, LocalDate.now(), LocalDate.now()))
                    .isEqualTo(List.of(todayStock));
            then(repository).should().findById(anyString());
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";
            given(repository.findById(anyString())).willReturn(Optional.empty());
            assertThrows(CompanyNotFoundException.class, () -> adapter.findAllByCompanyCodeAndDateRange(companyCode, LocalDate.now(), LocalDate.now()));
            then(repository).should().findById(anyString());
        }
    }

    @Nested
    @DisplayName("save")
    public class Save {
        @Test
        void shouldAddStockToCompany() {
            final String companyCode = "bc4b7d";
            CompanyDocument document = CompanyDocument.builder()
                    .code(companyCode)
                    .stocks(new ArrayList<>())
                    .build();

            given(repository.findById(anyString())).willReturn(Optional.of(document));
            adapter.save(companyCode, new Stock());

            verify(repository, atLeastOnce()).save(any(CompanyDocument.class));
            then(repository).should().findById(anyString());
            then(repository).should().save(any(CompanyDocument.class));
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";
            given(repository.findById(anyString())).willReturn(Optional.empty());
            assertThrows(CompanyNotFoundException.class, () -> adapter.save(companyCode, new Stock()));
            then(repository).should().findById(anyString());
        }
    }
}