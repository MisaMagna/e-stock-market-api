package com.cognizant.fse2.estockmarketapi.application.web.controller;

import com.cognizant.fse2.estockmarketapi.application.web.WebTest;
import com.cognizant.fse2.estockmarketapi.application.web.dto.StockPriceDto;
import com.cognizant.fse2.estockmarketapi.domain.exception.CompanyNotFoundException;
import com.cognizant.fse2.estockmarketapi.domain.model.Stock;
import com.cognizant.fse2.estockmarketapi.domain.port.StockHandlerPort;
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
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StockController.class)
@DisplayName("Stock Controller")
class StockControllerTest implements WebTest {

    private static final String BASE_PATH = "/api/v1.0/market/stock";

    @Autowired
    private MockMvc rest;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private StockHandlerPort handlerPort;

    @Nested
    @DisplayName("/get/{companyCode}/{startDate}/{endDate}")
    public class GetBy {
        @Test
        @DisplayName("should return stock list")
        void shouldReturnStockList() {
            final String companyCode = "bc4b7d";
            final String startDate = "2022-06-06";
            final String endDate = "2022-06-08";

            given(handlerPort.get(anyString(), any(LocalDate.class), any(LocalDate.class)))
                    .willReturn(List.of(mock(Stock.class)));

            try {
                rest.perform(get(BASE_PATH + "/get/" + companyCode + "/" + startDate + "/" + endDate))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().get(anyString(), any(LocalDate.class), any(LocalDate.class));
        }

        @Test
        @DisplayName("should return not found on empty company code")
        void shouldReturnNotFoundOnEmptyCompanyCode() {
            final String startDate = "2022-06-06";
            final String endDate = "2022-06-08";
            given(handlerPort.get(anyString(), any(LocalDate.class), any(LocalDate.class)))
                    .willReturn(List.of(mock(Stock.class)));

            try {
                rest.perform(get(BASE_PATH + "/get/" + "/" + startDate + "/" + endDate))
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
            final String startDate = "2022-06-06";
            final String endDate = "2022-06-08";

            final String message = String.format("Company with id %s does not exists", companyCode);

            willThrow(new CompanyNotFoundException(message))
                    .given(handlerPort).get(anyString(), any(LocalDate.class), any(LocalDate.class));

            try {
                rest.perform(get(BASE_PATH + "/get/" + companyCode + "/" + startDate + "/" + endDate))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().get(anyString(), any(LocalDate.class), any(LocalDate.class));
        }

        @Test
        @DisplayName("should return bad request on invalid date")
        void shouldReturnBadRequestOnInvalidDate() {
            final String companyCode = "bc4b7d";
            final String startDate = "2022-xx-06";
            final String endDate = "2022-xx-08";

            try {
                rest.perform(get(BASE_PATH + "/get/" + companyCode + "/" + startDate + "/" + endDate))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).shouldHaveNoInteractions();
        }
    }

    @Nested
    @DisplayName("/add/{companyName}")
    public class Add {
        @Test
        @DisplayName("should create stock")
        void shouldCreateStock() {
            final String companyCode = "bc4b7d";
            StockPriceDto price = StockPriceDto.builder()
                    .price(BigDecimal.TEN)
                    .build();

            try {
                rest.perform(post(BASE_PATH + "/add/" + companyCode)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(price)))
                        .andExpect(status().isCreated());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            verify(handlerPort, only()).add(anyString(), any(Stock.class));
            then(handlerPort).should().add(anyString(), any(Stock.class));
        }

        @Test
        @DisplayName("should return not found on empty company code")
        void shouldReturnNotFoundOnEmptyCompanyCode() {
            try {
                rest.perform(post(BASE_PATH + "/add/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new StockPriceDto())))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            verify(handlerPort, never()).add(anyString(), any(Stock.class));
            then(handlerPort).shouldHaveNoInteractions();
        }

        @Test
        @DisplayName("should return not found on not existing company code")
        void shouldReturnNotFoundOnNotExistingCompanyCode() {
            final String companyCode = "bc4b7d";
            final String message = String.format("Company with id %s does not exists", companyCode);

            willThrow(new CompanyNotFoundException(message))
                    .given(handlerPort).add(anyString(), any(Stock.class));

            try {
                rest.perform(post(BASE_PATH + "/add/" + companyCode)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new StockPriceDto())))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().add(anyString(), any(Stock.class));
        }

        @Test
        @DisplayName("should return bad request")
        void shouldReturnBadRequest() {
            final String companyCode = "bc4b7d";
            final String message = "Price cannot be null";
            willThrow(new ConstraintViolationException(message, Collections.emptySet()))
                    .given(handlerPort).add(anyString(), any(Stock.class));

            try {
                rest.perform(post(BASE_PATH + "/add/" + companyCode)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(new StockPriceDto())))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            then(handlerPort).should().add(anyString(), any(Stock.class));
        }
    }
}