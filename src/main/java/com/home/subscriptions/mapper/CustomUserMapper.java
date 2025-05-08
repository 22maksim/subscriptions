package com.home.subscriptions.mapper;

import com.home.subscriptions.model.CustomUser;
import com.home.subscriptions.model.dto.UserResponseDto;

import java.util.Objects;

public class CustomUserMapper {

    public static UserResponseDto userToUserResponseDto(CustomUser user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .subscriptions(
                        user.getSubscriptions().stream()
                                .filter(Objects::nonNull)
                                .map(SubscriptionMapper::toDto)
                                .toList()
                )
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
