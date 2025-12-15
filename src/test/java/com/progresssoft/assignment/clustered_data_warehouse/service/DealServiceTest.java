package com.progresssoft.assignment.clustered_data_warehouse.service;


import com.progresssoft.assignment.clustered_data_warehouse.api.dto.DealRequestDTO;
import com.progresssoft.assignment.clustered_data_warehouse.api.dto.ImportSummaryDTO;
import com.progresssoft.assignment.clustered_data_warehouse.model.Deal;
import com.progresssoft.assignment.clustered_data_warehouse.repository.DealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @InjectMocks
    private DealService dealService;

    @Test
    public void shouldSuccessfullyImportOneDeal(){

        DealRequestDTO request =  DealRequestDTO.builder()
                .dealUniqueId("")
                .fromCurrencyIso("MAD")
                .toCurrencyIso("USD")
                .dealAmount(BigDecimal.TEN)
                .dealTimestamp(LocalDateTime.now())
                .build();

        when(dealRepository.existsByDealUniqueId("DEAL-001")).thenReturn(false);
        dealService.processSingleDeal(request);
        verify(dealRepository, times(1)).save(any(Deal.class));

    }


}