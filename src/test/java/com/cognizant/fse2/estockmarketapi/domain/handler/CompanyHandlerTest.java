package com.cognizant.fse2.estockmarketapi.domain.handler;

import com.cognizant.fse2.estockmarketapi.AppTest;
import com.cognizant.fse2.estockmarketapi.domain.DomainTest;
import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Company Handler")
class CompanyHandlerTest implements DomainTest, AppTest {

    @InjectMocks
    private CompanyHandler handler;

    @Mock
    private CompanyPersistencePort port;

    @BeforeEach
    void setUp() {
        handler = new CompanyHandler(port);
    }

    @Nested
    @DisplayName("getAll")
    public class GetAll {
        @Test
        @DisplayName("should return companies with today stock")
        void shouldReturnCompaniesWithTodayStock() {
            Stock todayStock = Stock.builder()
                    .date(LocalDate.now())
                    .build();

            Stock yesterdayStock = Stock.builder()
                    .date(LocalDate.now().minusDays(1))
                    .build();

            Company company = Company.builder()
                    .stocks(List.of(todayStock, yesterdayStock))
                    .build();

            Company todayCompany = Company.builder()
                    .stocks(List.of(todayStock))
                    .build();

            given(port.findAll()).willReturn(List.of(company, company));
            assertThat(handler.getAll()).isEqualTo(List.of(todayCompany, todayCompany));
            then(port).should().findAll();
        }
    }

    @Nested
    @DisplayName("getOne")
    public class GetOne {
        @Test
        @DisplayName("should return company with today stock")
        void shouldReturnCompanyWithTodayStock() {
            final String companyCode = "bc4b7d";
            Stock todayStock = Stock.builder()
                    .date(LocalDate.now())
                    .build();

            Stock yesterdayStock = Stock.builder()
                    .date(LocalDate.now().minusDays(1))
                    .build();

            Company company = Company.builder()
                    .stocks(List.of(todayStock, yesterdayStock))
                    .build();

            Company todayCompany = Company.builder()
                    .stocks(List.of(todayStock))
                    .build();

            given(port.findByCode(anyString())).willReturn(company);
            assertThat(handler.getOne(companyCode)).isEqualTo(todayCompany);
            then(port).should().findByCode(anyString());
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";

            String message = String.format("Company with id %s does not exists", companyCode);
            willThrow(new CompanyNotFoundException(message)).given(port)
                    .findByCode(anyString());

            CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class,
                    () -> handler.getOne(companyCode));

            assertThat(exception.getMessage()).isEqualTo(message);
            then(port).should().findByCode(anyString());
        }
    }

    @Nested
    @DisplayName("create")
    public class Create {
        @Test
        @DisplayName("should create company")
        void shouldCreateCompany() {
            Company company = Company.builder()
                    .name("Company 1")
                    .website("example.com")
                    .build();

            Company newCompany = Company.builder()
                    .code("bc4b7d")
                    .name("Company 1")
                    .website("example.com")
                    .build();

            given(port.save(any(Company.class))).willReturn(newCompany);
            assertThat(handler.create(company)).isEqualTo(newCompany);
            then(port).should().save(company);
        }
    }

    @Nested
    @DisplayName("delete")
    public class Delete {
        @Test
        void shouldDelete() {
            final String companyCode = "bc4b7d";
            handler.delete(companyCode);
            verify(port, only()).deleteByCode(anyString());
            then(port).should().deleteByCode(anyString());
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";

            String message = String.format("Company with id %s does not exists", companyCode);
            willThrow(new CompanyNotFoundException(message)).given(port)
                    .deleteByCode(anyString());

            CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class,
                    () -> handler.delete(companyCode));

            assertThat(exception.getMessage()).isEqualTo(message);
            then(port).should().deleteByCode(anyString());
        }
    }
}