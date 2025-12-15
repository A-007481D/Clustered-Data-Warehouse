package com.progresssoft.assignment.clustered_data_warehouse.api.controller;


import com.progresssoft.assignment.clustered_data_warehouse.api.dto.DealRequestDTO;
import com.progresssoft.assignment.clustered_data_warehouse.api.dto.ImportSummaryDTO;
import com.progresssoft.assignment.clustered_data_warehouse.model.Deal;
import com.progresssoft.assignment.clustered_data_warehouse.service.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
@Slf4j
public class DealController {

    private final DealService dealService;

    @PostMapping
    public ResponseEntity<String> importDeals(@RequestBody @Valid List<DealRequestDTO> dealRequests) {

        log.info("Recieved request to import {} deals ", dealRequests.size());
        ImportSummaryDTO summary = dealService.importDeals(dealRequests);
        return ResponseEntity.ok().body(summary.getErrorMessages().get(0));
    }


}
