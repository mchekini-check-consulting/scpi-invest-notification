package net.checkconsulting.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailPlannedInvestPartnerNotificationDto {
    private String investorName;
    private String reason;
    private String frequency;
    private Float amount;
    private Integer debitDayOfMonth;
}
