package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.AppTest;
import com.cognizant.fse2.estockmarketapi.application.web.WebTest;
import com.cognizant.fse2.estockmarketapi.application.web.dto.CompanyDetailDto;
import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Company;
import com.cognizant.fse2.estockmarketapi.domain.model.StockExchange;
import com.cognizant.fse2.estockmarketapi.domain.port.CompanyHandlerPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CompanyController.class)
@DisplayName("Company Controller")
class CompanyControllerTest implements WebTest, AppTest {

    private static final String BASE_PATH = "/api/v1.0/market/company";

    @Autowired
    private MockMvc rest;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CompanyHandlerPort handlerPort;

    @Nested
    @DisplayName("/get-all")
    public class GetAll {
        @Test
        @DisplayName("should return company list")
        void shouldReturnCompanyList() {
            given(handlerPort.getAll()).willReturn(List.of(mock(Company.class)));

            try {
                rest.perform(get(BASE_PATH + "/get-all"))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().getAll();
        }
    }

    @Nested
    @DisplayName("/info/{companyCode}")
    public class GetInfo {

        @Test
        @DisplayName("should return company")
        void shouldReturnCompany() {
            final String companyCode = "bc4b7d";
            given(handlerPort.getOne(anyString())).willReturn(mock(Company.class));

            try {
                rest.perform(get(BASE_PATH + "/info/" + companyCode))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().getOne(anyString());
        }

        @Test
        @DisplayName("should return not found on empty company code")
        void shouldReturnNotFoundOnEmptyCompanyCode() {
            given(handlerPort.getOne(anyString())).willReturn(mock(Company.class));

            try {
                rest.perform(get(BASE_PATH + "/info/"))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).shouldHaveNoInteractions();
        }

        @Test
        @DisplayName("should return not found on not existing company code")
        void shouldReturnNotFoundOnNotExistingCompanyCode() {
            final String companyCode = "bc4b7d";
            final String message = String.format("Company with id %s does not exists", companyCode);

            willThrow(new CompanyNotFoundException(message)).given(handlerPort).getOne(anyString());

            try {
                rest.perform(get(BASE_PATH + "/info/" + companyCode))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().getOne(anyString());
        }
    }

    @Nested
    @DisplayName("/register")
    public class Register {
        @Test
        @DisplayName("should create company")
        void shouldReturnNewCompany() {
            CompanyDetailDto companyDetail = CompanyDetailDto.builder()
                    .name("Company 1")
                    .CEO("CEO 1")
                    .turnover(BigDecimal.valueOf(10000000))
                    .website("example.com")
                    .exchanges(List.of(StockExchange.NSE))
                    .build();

            given(handlerPort.create(any(Company.class))).willReturn(mock(Company.class));

            try {
                rest.perform(post(BASE_PATH + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(companyDetail)))
                        .andExpect(status().isCreated());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().create(any(Company.class));
        }

        @Test
        @DisplayName("should return bad request on constraint validation")
        void shouldReturnBadRequestOnConstraintValidation() {
            final String message = String.format("Turnover must be greater than %d", 10000000);
            willThrow(new ConstraintViolationException(message, Collections.emptySet()))
                    .given(handlerPort).create(any(Company.class));

            try {
                rest.perform(post(BASE_PATH + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new CompanyDetailDto())))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().create(any(Company.class));
        }

        @Test
        @DisplayName("should return bad request on invalid enum")
        void shouldReturnBadRequestOnInvalidEnum() {
            CompanyDetailDto detail = CompanyDetailDto.builder()
                    .name("Company 1")
                    .CEO("CEO 1")
                    .turnover(BigDecimal.valueOf(10000000))
                    .website("example.com")
                    .exchanges(List.of(StockExchange.NSE))
                    .build();

            try {
                rest.perform(post(BASE_PATH + "/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(detail)
                                        .replace("NSE", "ASD")))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).shouldHaveNoInteractions();
        }
    }

    @Nested
    @DisplayName("/delete/{companyCode}")
    public class Delete {
        @Test
        @DisplayName("should delete company")
        void shouldDeleteCompany() {
            final String companyCode = "bc4b7d";

            try {
                rest.perform(delete(BASE_PATH + "/delete/" + companyCode))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            verify(handlerPort, only()).delete(anyString());
            then(handlerPort).should().delete(anyString());
        }

        @Test
        @DisplayName("should return not found on empty company code")
        void shouldReturnNotFoundOnEmptyCompanyCode() {

            try {
                rest.perform(get(BASE_PATH + "/delete/"))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            verify(handlerPort, never()).delete(anyString());
            then(handlerPort).shouldHaveNoInteractions();
        }


        @Test
        @DisplayName("should return not found on not existing company code")
        void shouldReturnNotFoundOnNotExistingCompanyCode() {
            final String companyCode = "bc4b7d";
            final String message = String.format("Company with id %s does not exists", companyCode);

            willThrow(new CompanyNotFoundException(message)).given(handlerPort).delete(anyString());

            try {
                rest.perform(delete(BASE_PATH + "/delete/" + companyCode))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            verify(handlerPort, only()).delete(anyString());
            then(handlerPort).should().delete(anyString());
        }
    }
}