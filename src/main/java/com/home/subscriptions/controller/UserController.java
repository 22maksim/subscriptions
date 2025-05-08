package com.home.subscriptions.controller;

import com.home.subscriptions.model.dto.*;
import com.home.subscriptions.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userServiceImpl;

    @Operation(summary = "Создать пользователя", description = "Создает нового пользователя")
    @PostMapping
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto requestDto) {
        return userServiceImpl.createUser(requestDto);
    }

    @Operation(summary = "Обновление пользователя", description = "Обновляет имя пользователя, возможно расширение")
    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @RequestBody @Valid UserUpdateRequestDto requestDto, @PathVariable @Positive long id) {
        return userServiceImpl.updateUser(requestDto, id);
    }

    @Operation(summary = "Получить пользователя по id", description = "Позволяет получить пользователя по id")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable @Positive long id) {
        return userServiceImpl.getUserById(id);
    }

    @Operation(summary = "Удаляет пользователя", description = "Удаляет пользователя по id")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable @Positive long id) {
        userServiceImpl.deleteUserById(id);
    }

    @Operation(summary = "Добавить подписку", description = "Добавляет подписку пользователю")
    @PostMapping("/{id}/subscriptions")
    public UserResponseDto addSubscription(
            @RequestBody @Valid SubscriptionRequestDto requestDto, @Positive @PathVariable long id) {
        return userServiceImpl.addSubscription(requestDto, id);
    }

    @Operation(summary = "Получить все подписки",
            description = "Позволяет получить все подписки пользователя с пагинацией")
    @GetMapping("/{id}/subscriptions")
    public Page<SubscriptionResponseDto> getSubscriptionsByUserId(
            @PathVariable @Positive long id,
            @RequestParam(name = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(name = "size", defaultValue = "10") @Min(3) int size) {
        return userServiceImpl.getSubscriptionsByUserId(id, page, size);
    }

    @Operation(summary = "Удаляет подписку",
            description = "Удаляет подписку пользователь с определенным id, по id подписки")
    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    public List<SubscriptionResponseDto> deleteSubscriptions(@PathVariable @Positive long id, @PathVariable @Positive long sub_id) {
        return userServiceImpl.deleteSubscriptions(id, sub_id);
    }
}
