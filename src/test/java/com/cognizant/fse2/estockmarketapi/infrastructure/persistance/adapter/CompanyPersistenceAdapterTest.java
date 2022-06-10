package com.cognizant.fse2.estockmarketapi.infrastructure.persistance.adapter;

import com.cognizant.fse2.estockmarketapi.AppTest;
import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Company Persistence Adapter")
class CompanyPersistenceAdapterTest implements PersistenceTest, AppTest {

    @InjectMocks
    private CompanyPersistenceAdapter adapter;

    @Mock
    private CompanyRepository repository;

    @BeforeEach
    void setUp() {
        adapter = new CompanyPersistenceAdapter(repository);
    }

    @Nested
    @DisplayName("findAll")
    public class FindAll {
        @Test
        @DisplayName("should return companies")
        void shouldReturnCompanies() {
            CompanyDocument document = CompanyDocument.builder()
                    .code("bc4b7d")
                    .build();

            Company company = Company.builder()
                    .code("bc4b7d")
                    .build();

            given(repository.findAll()).willReturn(List.of(document, document));
            assertThat(adapter.findAll()).isEqualTo(List.of(company, company));
            then(repository).should().findAll();
        }
    }

    @Nested
    @DisplayName("findByCode")
    public class FindByCode {
        @Test
        @DisplayName("should return company")
        void shouldReturnCompany() {
            final String companyCode = "bc4b7d";
            given(repository.findById(anyString())).willReturn(Optional.of(new CompanyDocument()));
            assertThat(adapter.findByCode(companyCode)).isEqualTo(new Company());
            then(repository).should().findById(anyString());
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";
            given(repository.findById(anyString())).willReturn(Optional.empty());
            assertThrows(CompanyNotFoundException.class, () -> adapter.findByCode(companyCode));
            then(repository).should().findById(anyString());
        }
    }

    @Nested
    @DisplayName("save")
    public class Save {
        @Test
        @DisplayName("should create company")
        void shouldCreateCompany() {
            CompanyDocument document = CompanyDocument.builder()
                    .code("bc4b7d")
                    .build();

            Company company = Company.builder()
                    .code("bc4b7d")
                    .build();

            given(repository.save(any(CompanyDocument.class))).willReturn(document);
            assertThat(adapter.save(company)).isEqualTo(company);
            then(repository).should().save(any(CompanyDocument.class));
        }
    }

    @Nested
    @DisplayName("deleteByCode")
    public class DeleteByCode {
        @Test
        @DisplayName("should delete company")
        void shouldDeleteCompany() {
            final String companyCode = "bc4b7d";
            given(repository.findById(anyString())).willReturn(Optional.of(new CompanyDocument()));
            adapter.deleteByCode(companyCode);

            verify(repository, atLeastOnce()).deleteById(anyString());
            then(repository).should().findById(anyString());
            then(repository).should().deleteById(anyString());
        }

        @Test
        @DisplayName("should throw company not found exception")
        void shouldThrowCompanyNotFoundException() {
            final String companyCode = "bc4b7d";
            given(repository.findById(anyString())).willReturn(Optional.empty());
            assertThrows(CompanyNotFoundException.class, () -> adapter.deleteByCode(companyCode));
            then(repository).should().findById(anyString());
        }
    }
}