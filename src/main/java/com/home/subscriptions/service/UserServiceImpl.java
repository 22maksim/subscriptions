package com.home.subscriptions.service;

import com.home.subscriptions.exception.CustomUserNotFoundException;
import com.home.subscriptions.exception.EntityExistsException;
import com.home.subscriptions.exception.SubscriptionNotFoundException;
import com.home.subscriptions.mapper.CustomUserMapper;
import com.home.subscriptions.mapper.SubscriptionMapper;
import com.home.subscriptions.model.CustomUser;
import com.home.subscriptions.model.Subscription;
import com.home.subscriptions.model.dto.*;
import com.home.subscriptions.repository.SubscriptionRepository;
import com.home.subscriptions.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;


    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EntityExistsException("Email already in use");
        }

        CustomUser customUser = new CustomUser();
        customUser.setUsername(requestDto.getUsername());
        customUser.setEmail(requestDto.getEmail());

        return CustomUserMapper.userToUserResponseDto(userRepository.save(customUser));
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto requestDto, long id) {
        CustomUser customUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomUserNotFoundException("User Not Found"));
        customUser.setUsername(requestDto.getUsername());

        return CustomUserMapper.userToUserResponseDto(userRepository.save(customUser));
    }

    @Override
    @Transactional
    public UserResponseDto getUserById(long id) {
        return CustomUserMapper.userToUserResponseDto(userRepository.findById(id)
                .orElseThrow(()  -> new CustomUserNotFoundException("User Not Found")));
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserResponseDto addSubscription(SubscriptionRequestDto requestDto, long id) {
        CustomUser customUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomUserNotFoundException("User Not Found"));

        if (customUser.getSubscriptions().stream()
                .map(Subscription::getServiceName)
                .anyMatch(sub -> sub.equals(requestDto.getService_name()))
        ) {
            throw new EntityExistsException("Subscription already exists");
        }

        Subscription subscription = SubscriptionMapper.fromDto(requestDto);

        subscription.setUser(customUser);
        subscription = subscriptionRepository.save(subscription);
        customUser.getSubscriptions().add(subscription);

        return CustomUserMapper.userToUserResponseDto(userRepository.save(customUser));
    }

    @Override
    @Transactional
    public Page<SubscriptionResponseDto> getSubscriptionsByUserId(long id, int page, int size) {
        if (!userRepository.existsById(id)) {
            throw new CustomUserNotFoundException("User Not Found");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());

        return subscriptionRepository.findByUserId(id, pageable)
                .map(SubscriptionMapper::toDto);
    }

    @Override
    @Transactional
    public List<SubscriptionResponseDto> deleteSubscriptions(long id, long sub_id) {
        CustomUser user =  userRepository.findById(id)
                .orElseThrow(() -> new CustomUserNotFoundException("User Not Found"));

        if (user.getSubscriptions().isEmpty()) {
            throw new IllegalArgumentException("User does not have subscriptions");
        }

        user.getSubscriptions().remove(subscriptionRepository.findById(sub_id)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription Not Found")));

        userRepository.save(user);

        return user.getSubscriptions().stream()
                .filter(Objects::nonNull)
                .map(SubscriptionMapper::toDto)
                .toList();
    }
}
