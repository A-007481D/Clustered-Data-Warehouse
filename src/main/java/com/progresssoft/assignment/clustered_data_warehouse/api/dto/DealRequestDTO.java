package com.progresssoft.assignment.clustered_data_warehouse.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DealRequestDTO {

    @NotBlank(message = "Deal unique ID  is required")
    private String dealUniqueId;

    @NotBlank(message = "From currency ISO code is required")
    @Size(min = 3,  max = 3 , message = "From currency ISO must be exactly 3 characters long")
    private String fromCurrencyIso;

    @NotBlank(message = "To currency ISO CODE is required")
    @Size(min = 3,  max = 3 , message = "TO currency ISO must be exactly 3 characters long")
    private String toCurrencyIso;

    @Positive(message = "deal amount must be > 0")
    private BigDecimal dealAmount;

    private LocalDateTime dealTimestamp;



}
