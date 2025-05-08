package com.home.subscriptions.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.subscriptions.model.SubscriptionStatus;
import com.home.subscriptions.model.dto.*;
import com.home.subscriptions.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        UserResponseDto responseDto = new UserResponseDto(
                1L, "test@example.com", "Test User", List.of(), Instant.now(), Instant.now());

        given(userService.getUserById(1L)).willReturn(responseDto);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void addSubscription_ShouldReturnUserWithSubscription() throws Exception {
        SubscriptionRequestDto requestDto = new SubscriptionRequestDto(
                "Netflix", BigDecimal.valueOf(99.99), "USD", Instant.now(), Instant.now(), SubscriptionStatus.ACTIVE);
        UserResponseDto responseDto = new UserResponseDto(
                1L, "test@example.com", "Test User", List.of(), Instant.now(), Instant.now());

        given(userService.addSubscription(any(SubscriptionRequestDto.class), eq(1L))).willReturn(responseDto);

        mockMvc.perform(post("/users/1/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

}