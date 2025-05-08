package com.home.subscriptions.controller;

import com.home.subscriptions.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionServiceImpl;

    @Operation(summary = "Получить топ три подписки")
    @GetMapping("/top")
    public List<String> getTopThreeSubscriptions() {
        return subscriptionServiceImpl.getTopThreeSubscriptions();
    }

}
