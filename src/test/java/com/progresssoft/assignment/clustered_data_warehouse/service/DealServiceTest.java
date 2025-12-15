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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .dealUniqueId("DEAL-001")
                .fromCurrencyIso("MAD")
                .toCurrencyIso("USD")
                .dealAmount(BigDecimal.TEN)
                .dealTimestamp(LocalDateTime.now())
                .build();

        when(dealRepository.existsByDealUniqueId("DEAL-001")).thenReturn(false);
        dealService.processSingleDeal(request);
        verify(dealRepository, times(1)).save(any(Deal.class));

    }


    @Test
    public void shouldHandleBatchFailureWithoutRollback(){
        DealRequestDTO validDeal = DealRequestDTO.builder()
                .dealUniqueId("DEALL-VALID")
                .fromCurrencyIso("MAD")
                .toCurrencyIso("JOD")
                .dealAmount(BigDecimal.TEN)
                .dealTimestamp(LocalDateTime.now())
                .build();

        DealRequestDTO duplicateDeal = DealRequestDTO.builder()
                .dealUniqueId("DEAL-DUPLICATE")
                .fromCurrencyIso("USD")
                .toCurrencyIso("EUR")
                .dealAmount(BigDecimal.ONE).dealTimestamp(LocalDateTime.now())
                .build();

        when(dealRepository.existsByDealUniqueId("DEALL-VALID")).thenReturn(false);
        when(dealRepository.existsByDealUniqueId("DEAL-DUPLICATE")).thenReturn(true); // should trigger failure

        ImportSummaryDTO summary = dealService.importDeals(List.of(validDeal, duplicateDeal));

        assertEquals(1, summary.getSuccessCount(), "should have 1 successful import");
        assertEquals(1, summary.getFailureCount(), "should have 1 failed import");

        verify(dealRepository, times(1)).save(any(Deal.class)); // only valid deal should be saved
    }

    @Test
    public void ShouldHandleUnexpectedDatabaseCrash() {
        DealRequestDTO validDeal = DealRequestDTO.builder()
                .dealUniqueId("DEAL-CRASH")
                .fromCurrencyIso("MAD")
                .toCurrencyIso("JOD")
                .dealAmount(BigDecimal.TEN)
                .dealTimestamp(LocalDateTime.now())
                .build();

        when(dealRepository.existsByDealUniqueId("DEAL-VALID")).thenReturn(false);
        doThrow(new RuntimeException("Database connection lost")).when(dealRepository).save(any(Deal.class));


        ImportSummaryDTO summary = dealService.importDeals(List.of(validDeal));

        assertEquals(0, summary.getSuccessCount());
        assertEquals(1, summary.getFailureCount());

        assertEquals("DEAL-CRASH: Database connection lost", summary.getErrorMessages().get(0));
    }


}