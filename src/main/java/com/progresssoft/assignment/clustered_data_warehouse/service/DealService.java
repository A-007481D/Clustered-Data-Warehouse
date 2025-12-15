package com.progresssoft.assignment.clustered_data_warehouse.service;

import com.progresssoft.assignment.clustered_data_warehouse.api.dto.DealRequestDTO;
import com.progresssoft.assignment.clustered_data_warehouse.api.dto.ImportSummaryDTO;
import com.progresssoft.assignment.clustered_data_warehouse.model.Deal;
import com.progresssoft.assignment.clustered_data_warehouse.repository.DealRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DealService {
    private final DealRepository dealRepository;

    public void processSingleDeal(DealRequestDTO request){
        log.info("Processing single deal: {}", request);

        if(dealRepository.existsByDealUniqueId(request.getDealUniqueId())) {
            throw new IllegalArgumentException("Duplicate Deal!,this deal already exists");
        }


        Deal dealEntity = Deal.builder()
                .dealUniqueId(request.getDealUniqueId())
                .fromCurrencyIso(request.getFromCurrencyIso())
                .toCurrencyIso(request.getToCurrencyIso())
                .dealAmount(request.getDealAmount())
                .dealTimestamp(request.getDealTimestamp())
                .build();

        dealRepository.save(dealEntity);

    }




}
