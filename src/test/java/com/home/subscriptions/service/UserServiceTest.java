package com.home.subscriptions.service;

import com.home.subscriptions.exception.CustomUserNotFoundException;
import com.home.subscriptions.exception.EntityExistsException;
import com.home.subscriptions.model.CustomUser;
import com.home.subscriptions.model.Subscription;
import com.home.subscriptions.model.SubscriptionStatus;
import com.home.subscriptions.model.dto.*;
import com.home.subscriptions.repository.SubscriptionRepository;
import com.home.subscriptions.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final CustomUser testUser = CustomUser.builder()
            .id(1L)
            .username("testUser")
            .email("test@example.com")
            .build();

    private final Subscription testSubscription = Subscription.builder()
            .id(1L)
            .serviceName("Netflix")
            .price(BigDecimal.valueOf(9.99))
            .currency("USD")
            .startDate(Instant.now())
            .endDate(Instant.now().plus(30, ChronoUnit.DAYS))
            .status(SubscriptionStatus.ACTIVE)
            .user(testUser)
            .build();

    @Test
    void createUser_ShouldThrowWhenEmailExists() {
        UserRequestDto requestDto = new UserRequestDto("newUser", "existing@example.com");
        given(userRepository.existsByEmail(anyString())).willReturn(true);

        assertThatThrownBy(() -> userService.createUser(requestDto))
                .isInstanceOf(EntityExistsException.class)
                .hasMessageContaining("Email already in use");
    }

    @Test
    void updateUser_ShouldThrowWhenUserNotFound() {
        UserUpdateRequestDto requestDto = new UserUpdateRequestDto("updatedName");
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(requestDto, 999L))
                .isInstanceOf(CustomUserNotFoundException.class);
    }

    @Test
    void deleteUserById_ShouldCallRepository() {
        willDoNothing().given(userRepository).deleteById(1L);

        userService.deleteUserById(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void getSubscriptionsByUserId_ShouldReturnPage() {
        Page<Subscription> page = new PageImpl<>(List.of(testSubscription));
        given(userRepository.existsById(1L)).willReturn(true);
        given(subscriptionRepository.findByUserId(eq(1L), any(Pageable.class))).willReturn(page);

        Page<SubscriptionResponseDto> result = userService.getSubscriptionsByUserId(1L, 0, 10);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getService_name()).isEqualTo("Netflix");
    }

}