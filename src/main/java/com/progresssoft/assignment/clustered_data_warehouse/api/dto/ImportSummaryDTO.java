package com.progresssoft.assignment.clustered_data_warehouse.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportSummaryDTO {
    private int totalProcessed;
    private int successCount;
    private int failureCount;

    private LocalDateTime processEndTime;

    @Builder.Default
    private List<String> errorMessages = new ArrayList<>();

    public void addEerror(String message){
        this.errorMessages.add(message);
        this.failureCount++;
    }


    public void incrementSuccess(){
        this.successCount++;
    }

}
