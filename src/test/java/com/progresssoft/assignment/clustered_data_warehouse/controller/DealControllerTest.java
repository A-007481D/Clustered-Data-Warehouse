package com.progresssoft.assignment.clustered_data_warehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progresssoft.assignment.clustered_data_warehouse.api.controller.DealController;
import com.progresssoft.assignment.clustered_data_warehouse.api.dto.DealRequestDTO;
import com.progresssoft.assignment.clustered_data_warehouse.api.dto.ImportSummaryDTO;
import com.progresssoft.assignment.clustered_data_warehouse.service.DealService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DealController.class)
public class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private DealService dealService;

    @Test
    public void ShouldAcceptValidBatch() throws Exception {
        DealRequestDTO validDeal = DealRequestDTO.builder()
                .dealUniqueId("DEAL-OOK").fromCurrencyIso("JOD").toCurrencyIso("MAD").
                dealAmount(BigDecimal.TEN).dealTimestamp(LocalDateTime.now()).build();

        when(dealService.importDeals(any())).thenReturn(new ImportSummaryDTO());

        mockMvc.perform(post("/api/v1/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(validDeal))))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldRejectInvalidDeal() throws Exception {
        DealRequestDTO invalidDeal = DealRequestDTO.builder()
                .dealUniqueId("DEAL-BAD")
                .toCurrencyIso("MAD").dealTimestamp(LocalDateTime.now()).build();

        mockMvc.perform(post("/api/v1/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(invalidDeal)))).andExpect(status().isBadRequest());
    }
}
