package com.progresssoft.assignment.clustered_data_warehouse.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "deals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deal {
    @Id
    @Column(name = "deal_unique_id", unique = true, nullable = false)
    private String dealUniqueId;

    private String fromCurrencyIso;
    private String toCurrencyIso;

    private BigDecimal dealAmount;

    private LocalDateTime dealTimestamp;
}
