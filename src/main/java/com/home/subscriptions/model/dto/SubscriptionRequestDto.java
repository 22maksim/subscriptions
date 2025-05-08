package com.home.subscriptions.model.dto;

import com.home.subscriptions.model.SubscriptionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDto {

    @NotBlank
    private String service_name;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String currency;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @NotNull
    private SubscriptionStatus status;

}
