package com.home.subscriptions.model.dto;

import com.home.subscriptions.model.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDto {

    private Long id;

    private Long userId;

    private String service_name;

    private String currency;

    private BigDecimal price;

    private Instant startDate;

    private Instant endDate;

    private SubscriptionStatus status;

    private Instant createdAt;

}
