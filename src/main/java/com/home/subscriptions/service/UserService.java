package com.home.subscriptions.service;

import com.home.subscriptions.model.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto requestDto);

    UserResponseDto updateUser(UserUpdateRequestDto requestDto, long id);

    UserResponseDto getUserById(long id);

    void deleteUserById(long id);

    UserResponseDto addSubscription(SubscriptionRequestDto requestDto, long id);

    Page<SubscriptionResponseDto> getSubscriptionsByUserId(long id, int page, int size);

    List<SubscriptionResponseDto> deleteSubscriptions(long id, long sub_id);

}

