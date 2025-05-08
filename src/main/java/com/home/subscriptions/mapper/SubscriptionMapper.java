package com.home.subscriptions.mapper;

import com.home.subscriptions.model.Subscription;
import com.home.subscriptions.model.dto.SubscriptionRequestDto;
import com.home.subscriptions.model.dto.SubscriptionResponseDto;

public class SubscriptionMapper {

    public static SubscriptionResponseDto toDto(Subscription subscription) {
        return SubscriptionResponseDto.builder()
                .id(subscription.getId())
                .userId(subscription.getUser().getId())
                .service_name(subscription.getServiceName())
                .price(subscription.getPrice())
                .currency(subscription.getCurrency())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .status(subscription.getStatus())
                .createdAt(subscription.getCreatedAt())
                .build();
    }

    public static Subscription fromDto(SubscriptionRequestDto subscriptionRequestDto) {
        return Subscription.builder()
                .serviceName(subscriptionRequestDto.getService_name())
                .currency(subscriptionRequestDto.getCurrency())
                .price(subscriptionRequestDto.getPrice())
                .startDate(subscriptionRequestDto.getStartTime())
                .endDate(subscriptionRequestDto.getEndTime())
                .status(subscriptionRequestDto.getStatus())
                .price(subscriptionRequestDto.getPrice())
                .build();
    }

}
