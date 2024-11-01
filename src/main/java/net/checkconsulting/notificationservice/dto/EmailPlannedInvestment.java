package net.checkconsulting.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailPlannedInvestment {

    private String investorName;
    private String plannedInvestmentId;
}
